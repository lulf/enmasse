/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.activemq.artemis.integration.amqp;

import io.netty.buffer.ByteBuf;
import org.apache.activemq.artemis.protocol.amqp.proton.handler.EventHandler;
import org.apache.activemq.artemis.protocol.amqp.proton.handler.ProtonHandler;
import org.apache.activemq.artemis.spi.core.remoting.ReadyListener;
import org.apache.qpid.proton.amqp.Symbol;
import org.apache.qpid.proton.amqp.messaging.Accepted;
import org.apache.qpid.proton.amqp.messaging.Modified;
import org.apache.qpid.proton.amqp.messaging.Rejected;
import org.apache.qpid.proton.amqp.messaging.Released;
import org.apache.qpid.proton.amqp.messaging.Source;
import org.apache.qpid.proton.amqp.messaging.Target;
import org.apache.qpid.proton.amqp.messaging.TerminusDurability;
import org.apache.qpid.proton.engine.Connection;
import org.apache.qpid.proton.engine.Delivery;
import org.apache.qpid.proton.engine.Link;
import org.apache.qpid.proton.engine.Receiver;
import org.apache.qpid.proton.engine.Sender;
import org.apache.qpid.proton.engine.Session;
import org.apache.qpid.proton.engine.Transport;
import org.apache.qpid.proton.engine.impl.LinkMutator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Event handler for establishing outgoing links
 */
public class LinkInitiator implements EventHandler {
   private final LinkInfo linkInfo;
   private final Map<Link, org.apache.qpid.proton.amqp.transport.Target> initiatedReceivingLinks = new ConcurrentHashMap<>();
   private final Map<Link, org.apache.qpid.proton.amqp.transport.Source> initiatedSendingLinks = new ConcurrentHashMap<>();

   public LinkInitiator(LinkInfo linkInfo) {
      this.linkInfo = linkInfo;
   }

   @Override
   public void onAuthInit(ProtonHandler handler, Connection connection, boolean sasl) {

   }

   @Override
   public void onSaslRemoteMechanismChosen(ProtonHandler handler, String mech) {

   }

   @Override
   public void onAuthFailed(final ProtonHandler protonHandler, final Connection connection) {

   }

   @Override
   public void onAuthSuccess(final ProtonHandler protonHandler, final Connection connection) {

   }

   @Override
   public void onSaslMechanismsOffered(final ProtonHandler handler, final String[] mechanisms) {

   }

   @Override
   public void onInit(Connection connection) throws Exception {

   }

   @Override
   public void onLocalOpen(Connection connection) throws Exception {

   }

   @Override
   public void onRemoteOpen(org.apache.qpid.proton.engine.Connection connection) throws Exception {
      connection.session().open();
   }

   @Override
   public void onLocalClose(Connection connection) throws Exception {

   }

   @Override
   public void onRemoteClose(Connection connection) throws Exception {

   }

   @Override
   public void onFinal(Connection connection) throws Exception {

   }

   @Override
   public void onInit(Session session) throws Exception {

   }

   @Override
   public void onLocalOpen(Session session) throws Exception {

   }

   @Override
   public void onRemoteOpen(Session session) throws Exception {
      createLink(session);
   }

   @Override
   public void onLocalClose(Session session) throws Exception {

   }

   @Override
   public void onRemoteClose(Session session) throws Exception {

   }

   @Override
   public void onFinal(Session session) throws Exception {

   }

   @Override
   public void onInit(Link link) throws Exception {

   }

   @Override
   public void onLocalOpen(Link link) throws Exception {

   }

   @Override
   public void onRemoteOpen(Link link) throws Exception {
      ActiveMQAMQPLogger.LOGGER.infov("{0} onRemoteOpen", link.getName());
   }

   @Override
   public void onLocalClose(Link link) throws Exception {
      ActiveMQAMQPLogger.LOGGER.infov("{0} onLocalClose", link.getName());
   }

   @Override
   public void onRemoteClose(Link link) throws Exception {
      ActiveMQAMQPLogger.LOGGER.infov("Link {0} closed. Closing connection", link.getName());
      initiatedSendingLinks.remove(link);
      initiatedReceivingLinks.remove(link);
      link.getSession().getConnection().close();
   }

   @Override
   public void onFlow(Link link) throws Exception {

   }

   @Override
   public void onFinal(Link link) throws Exception {

   }

   @Override
   public void onRemoteDetach(Link link) throws Exception {
      ActiveMQAMQPLogger.LOGGER.infov("Link {0} detached. Closing connection", link.getName());
      initiatedSendingLinks.remove(link);
      initiatedReceivingLinks.remove(link);
      link.getSession().getConnection().close();
   }

   @Override
   public void onLocalDetach(Link link) throws Exception {
      ActiveMQAMQPLogger.LOGGER.infov("{0} onLocalDetach", link.getName());
   }

   @Override
   public void onDelivery(Delivery delivery) throws Exception {

   }

   @Override
   public boolean flowControl(ReadyListener readyListener) {
      if (this.linkInfo.getDirection().equals(Direction.in)) {
         readyListener.readyForWriting();
      }
      return true;
   }

   @Override
   public void onTransport(Transport transport) throws Exception {

   }

   @Override
   public void pushBytes(ByteBuf bytes) {

   }

   private void createLink(org.apache.qpid.proton.engine.Session session) throws Exception {
      Link initiatedLink = null;
      switch (linkInfo.getDirection()) {
         case out:
            initiatedLink = createSender(session);
            initiatedSendingLinks.put(initiatedLink, initiatedLink.getSource().copy());
            break;
         case in:
            initiatedLink = createReceiver(session);
            initiatedReceivingLinks.put(initiatedLink, initiatedLink.getTarget().copy());
            break;
      }
   }

   private Link createReceiver(Session session) {
      Receiver receiver;
      if (linkInfo.getLinkName() != null) {
         receiver = session.receiver(linkInfo.getLinkName());
      } else {
         receiver = session.receiver(linkInfo.getSourceAddress());
      }
      Target target = new Target();
      target.setAddress(linkInfo.getTargetAddress());
      if (!linkInfo.getCapabilities().isEmpty()) {
         target.setCapabilities(Symbol.getSymbol("qd.waypoint"));
      }
      receiver.setTarget(target);

      Source source = new Source();
      source.setAddress(linkInfo.getSourceAddress());
      source.setDurable(TerminusDurability.UNSETTLED_STATE);
      if (!linkInfo.getCapabilities().isEmpty()) {
         source.setCapabilities(linkInfo.getCapabilities().toArray(new Symbol[0]));
         source.setCapabilities(Symbol.getSymbol("qd.waypoint"));
      }
      receiver.setSource(source);
      receiver.open();
      return receiver;
   }

   private Link createSender(Session session) {
      Sender sender;
      if (linkInfo.getLinkName() != null) {
         sender = session.sender(linkInfo.getLinkName());
      } else {
         sender = session.sender(linkInfo.getTargetAddress());
      }
      Target target = new Target();
      target.setAddress(linkInfo.getTargetAddress());
      if (!linkInfo.getCapabilities().isEmpty()) {
         target.setCapabilities(Symbol.getSymbol("qd.waypoint"));
      }
      sender.setTarget(target);

      Source source = new Source();
      source.setAddress(linkInfo.getSourceAddress());
      source.setDurable(TerminusDurability.UNSETTLED_STATE);
      source.setOutcomes(Accepted.DESCRIPTOR_SYMBOL, Rejected.DESCRIPTOR_SYMBOL, Released.DESCRIPTOR_SYMBOL, Modified.DESCRIPTOR_SYMBOL);
      if (!linkInfo.getCapabilities().isEmpty()) {
         source.setCapabilities(Symbol.getSymbol("qd.waypoint"));
      }
      if (linkInfo.getConsumerPriority() != null) {
          Map<Symbol, Object> properties = sender.getRemoteProperties();
          if (properties == null) {
             properties = new HashMap<>();
          }
          properties.put(Symbol.getSymbol("priority"), linkInfo.getConsumerPriority());
      }
      sender.setSource(source);

      sender.open();
      return sender;
   }

   public void preserveInitiatedSourceTargetInfo(Link link) {
      if (initiatedReceivingLinks.containsKey(link)) {
         LinkMutator.setRemoteTarget(link, initiatedReceivingLinks.get(link));
      } else if (initiatedSendingLinks.containsKey(link)) {
         LinkMutator.setRemoteSource(link, initiatedSendingLinks.get(link));
      }
   }
}
