// +build !ignore_autogenerated

/*
 * Copyright 2018-2019, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */

// Code generated by deepcopy-gen. DO NOT EDIT.

package v1beta1

import (
	v1 "k8s.io/api/core/v1"
	runtime "k8s.io/apimachinery/pkg/runtime"
)

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *Address) DeepCopyInto(out *Address) {
	*out = *in
	out.TypeMeta = in.TypeMeta
	in.ObjectMeta.DeepCopyInto(&out.ObjectMeta)
	in.Spec.DeepCopyInto(&out.Spec)
	in.Status.DeepCopyInto(&out.Status)
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new Address.
func (in *Address) DeepCopy() *Address {
	if in == nil {
		return nil
	}
	out := new(Address)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyObject is an autogenerated deepcopy function, copying the receiver, creating a new runtime.Object.
func (in *Address) DeepCopyObject() runtime.Object {
	if c := in.DeepCopy(); c != nil {
		return c
	}
	return nil
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *AddressList) DeepCopyInto(out *AddressList) {
	*out = *in
	out.TypeMeta = in.TypeMeta
	in.ListMeta.DeepCopyInto(&out.ListMeta)
	if in.Items != nil {
		in, out := &in.Items, &out.Items
		*out = make([]Address, len(*in))
		for i := range *in {
			(*in)[i].DeepCopyInto(&(*out)[i])
		}
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new AddressList.
func (in *AddressList) DeepCopy() *AddressList {
	if in == nil {
		return nil
	}
	out := new(AddressList)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyObject is an autogenerated deepcopy function, copying the receiver, creating a new runtime.Object.
func (in *AddressList) DeepCopyObject() runtime.Object {
	if c := in.DeepCopy(); c != nil {
		return c
	}
	return nil
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *AddressPlanStatus) DeepCopyInto(out *AddressPlanStatus) {
	*out = *in
	if in.Resources != nil {
		in, out := &in.Resources, &out.Resources
		*out = make(map[string]float64, len(*in))
		for key, val := range *in {
			(*out)[key] = val
		}
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new AddressPlanStatus.
func (in *AddressPlanStatus) DeepCopy() *AddressPlanStatus {
	if in == nil {
		return nil
	}
	out := new(AddressPlanStatus)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *AddressSpace) DeepCopyInto(out *AddressSpace) {
	*out = *in
	out.TypeMeta = in.TypeMeta
	in.ObjectMeta.DeepCopyInto(&out.ObjectMeta)
	in.Spec.DeepCopyInto(&out.Spec)
	in.Status.DeepCopyInto(&out.Status)
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new AddressSpace.
func (in *AddressSpace) DeepCopy() *AddressSpace {
	if in == nil {
		return nil
	}
	out := new(AddressSpace)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyObject is an autogenerated deepcopy function, copying the receiver, creating a new runtime.Object.
func (in *AddressSpace) DeepCopyObject() runtime.Object {
	if c := in.DeepCopy(); c != nil {
		return c
	}
	return nil
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *AddressSpaceList) DeepCopyInto(out *AddressSpaceList) {
	*out = *in
	out.TypeMeta = in.TypeMeta
	in.ListMeta.DeepCopyInto(&out.ListMeta)
	if in.Items != nil {
		in, out := &in.Items, &out.Items
		*out = make([]AddressSpace, len(*in))
		for i := range *in {
			(*in)[i].DeepCopyInto(&(*out)[i])
		}
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new AddressSpaceList.
func (in *AddressSpaceList) DeepCopy() *AddressSpaceList {
	if in == nil {
		return nil
	}
	out := new(AddressSpaceList)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyObject is an autogenerated deepcopy function, copying the receiver, creating a new runtime.Object.
func (in *AddressSpaceList) DeepCopyObject() runtime.Object {
	if c := in.DeepCopy(); c != nil {
		return c
	}
	return nil
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *AddressSpaceSchema) DeepCopyInto(out *AddressSpaceSchema) {
	*out = *in
	out.TypeMeta = in.TypeMeta
	in.ObjectMeta.DeepCopyInto(&out.ObjectMeta)
	in.Spec.DeepCopyInto(&out.Spec)
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new AddressSpaceSchema.
func (in *AddressSpaceSchema) DeepCopy() *AddressSpaceSchema {
	if in == nil {
		return nil
	}
	out := new(AddressSpaceSchema)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyObject is an autogenerated deepcopy function, copying the receiver, creating a new runtime.Object.
func (in *AddressSpaceSchema) DeepCopyObject() runtime.Object {
	if c := in.DeepCopy(); c != nil {
		return c
	}
	return nil
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *AddressSpaceSchemaList) DeepCopyInto(out *AddressSpaceSchemaList) {
	*out = *in
	out.TypeMeta = in.TypeMeta
	in.ListMeta.DeepCopyInto(&out.ListMeta)
	if in.Items != nil {
		in, out := &in.Items, &out.Items
		*out = make([]AddressSpaceSchema, len(*in))
		for i := range *in {
			(*in)[i].DeepCopyInto(&(*out)[i])
		}
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new AddressSpaceSchemaList.
func (in *AddressSpaceSchemaList) DeepCopy() *AddressSpaceSchemaList {
	if in == nil {
		return nil
	}
	out := new(AddressSpaceSchemaList)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyObject is an autogenerated deepcopy function, copying the receiver, creating a new runtime.Object.
func (in *AddressSpaceSchemaList) DeepCopyObject() runtime.Object {
	if c := in.DeepCopy(); c != nil {
		return c
	}
	return nil
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *AddressSpaceSchemaSpec) DeepCopyInto(out *AddressSpaceSchemaSpec) {
	*out = *in
	if in.AuthenticationServices != nil {
		in, out := &in.AuthenticationServices, &out.AuthenticationServices
		*out = make([]string, len(*in))
		copy(*out, *in)
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new AddressSpaceSchemaSpec.
func (in *AddressSpaceSchemaSpec) DeepCopy() *AddressSpaceSchemaSpec {
	if in == nil {
		return nil
	}
	out := new(AddressSpaceSchemaSpec)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *AddressSpaceSpec) DeepCopyInto(out *AddressSpaceSpec) {
	*out = *in
	if in.AuthenticationService != nil {
		in, out := &in.AuthenticationService, &out.AuthenticationService
		*out = new(AuthenticationService)
		(*in).DeepCopyInto(*out)
	}
	if in.Endpoints != nil {
		in, out := &in.Endpoints, &out.Endpoints
		*out = make([]EndpointSpec, len(*in))
		for i := range *in {
			(*in)[i].DeepCopyInto(&(*out)[i])
		}
	}
	if in.Connectors != nil {
		in, out := &in.Connectors, &out.Connectors
		*out = make([]ConnectorSpec, len(*in))
		for i := range *in {
			(*in)[i].DeepCopyInto(&(*out)[i])
		}
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new AddressSpaceSpec.
func (in *AddressSpaceSpec) DeepCopy() *AddressSpaceSpec {
	if in == nil {
		return nil
	}
	out := new(AddressSpaceSpec)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *AddressSpaceStatus) DeepCopyInto(out *AddressSpaceStatus) {
	*out = *in
	if in.Messages != nil {
		in, out := &in.Messages, &out.Messages
		*out = make([]string, len(*in))
		copy(*out, *in)
	}
	if in.CACertificate != nil {
		in, out := &in.CACertificate, &out.CACertificate
		*out = make([]byte, len(*in))
		copy(*out, *in)
	}
	if in.EndpointStatus != nil {
		in, out := &in.EndpointStatus, &out.EndpointStatus
		*out = make([]EndpointStatus, len(*in))
		for i := range *in {
			(*in)[i].DeepCopyInto(&(*out)[i])
		}
	}
	if in.Connectors != nil {
		in, out := &in.Connectors, &out.Connectors
		*out = make([]ConnectorStatus, len(*in))
		for i := range *in {
			(*in)[i].DeepCopyInto(&(*out)[i])
		}
	}
	if in.Routers != nil {
		in, out := &in.Routers, &out.Routers
		*out = make([]RouterStatus, len(*in))
		for i := range *in {
			(*in)[i].DeepCopyInto(&(*out)[i])
		}
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new AddressSpaceStatus.
func (in *AddressSpaceStatus) DeepCopy() *AddressSpaceStatus {
	if in == nil {
		return nil
	}
	out := new(AddressSpaceStatus)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *AddressSpec) DeepCopyInto(out *AddressSpec) {
	*out = *in
	if in.Subscription != nil {
		in, out := &in.Subscription, &out.Subscription
		*out = new(SubscriptionSpec)
		(*in).DeepCopyInto(*out)
	}
	if in.Forwarders != nil {
		in, out := &in.Forwarders, &out.Forwarders
		*out = make([]ForwarderSpec, len(*in))
		copy(*out, *in)
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new AddressSpec.
func (in *AddressSpec) DeepCopy() *AddressSpec {
	if in == nil {
		return nil
	}
	out := new(AddressSpec)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *AddressStatus) DeepCopyInto(out *AddressStatus) {
	*out = *in
	if in.Messages != nil {
		in, out := &in.Messages, &out.Messages
		*out = make([]string, len(*in))
		copy(*out, *in)
	}
	if in.BrokerStatuses != nil {
		in, out := &in.BrokerStatuses, &out.BrokerStatuses
		*out = make([]BrokerStatus, len(*in))
		copy(*out, *in)
	}
	if in.PlanStatus != nil {
		in, out := &in.PlanStatus, &out.PlanStatus
		*out = new(AddressPlanStatus)
		(*in).DeepCopyInto(*out)
	}
	if in.Forwarders != nil {
		in, out := &in.Forwarders, &out.Forwarders
		*out = make([]ForwarderStatus, len(*in))
		for i := range *in {
			(*in)[i].DeepCopyInto(&(*out)[i])
		}
	}
	if in.Subscription != nil {
		in, out := &in.Subscription, &out.Subscription
		*out = new(SubscriptionStatus)
		(*in).DeepCopyInto(*out)
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new AddressStatus.
func (in *AddressStatus) DeepCopy() *AddressStatus {
	if in == nil {
		return nil
	}
	out := new(AddressStatus)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *AuthenticationService) DeepCopyInto(out *AuthenticationService) {
	*out = *in
	out.TypeMeta = in.TypeMeta
	in.ObjectMeta.DeepCopyInto(&out.ObjectMeta)
	if in.Overrides != nil {
		in, out := &in.Overrides, &out.Overrides
		*out = new(AuthenticationServiceSettings)
		(*in).DeepCopyInto(*out)
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new AuthenticationService.
func (in *AuthenticationService) DeepCopy() *AuthenticationService {
	if in == nil {
		return nil
	}
	out := new(AuthenticationService)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyObject is an autogenerated deepcopy function, copying the receiver, creating a new runtime.Object.
func (in *AuthenticationService) DeepCopyObject() runtime.Object {
	if c := in.DeepCopy(); c != nil {
		return c
	}
	return nil
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *AuthenticationServiceList) DeepCopyInto(out *AuthenticationServiceList) {
	*out = *in
	out.TypeMeta = in.TypeMeta
	in.ListMeta.DeepCopyInto(&out.ListMeta)
	if in.Items != nil {
		in, out := &in.Items, &out.Items
		*out = make([]AuthenticationService, len(*in))
		for i := range *in {
			(*in)[i].DeepCopyInto(&(*out)[i])
		}
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new AuthenticationServiceList.
func (in *AuthenticationServiceList) DeepCopy() *AuthenticationServiceList {
	if in == nil {
		return nil
	}
	out := new(AuthenticationServiceList)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyObject is an autogenerated deepcopy function, copying the receiver, creating a new runtime.Object.
func (in *AuthenticationServiceList) DeepCopyObject() runtime.Object {
	if c := in.DeepCopy(); c != nil {
		return c
	}
	return nil
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *AuthenticationServiceSettings) DeepCopyInto(out *AuthenticationServiceSettings) {
	*out = *in
	if in.CaCertSecret != nil {
		in, out := &in.CaCertSecret, &out.CaCertSecret
		*out = new(v1.SecretReference)
		**out = **in
	}
	if in.ClientCertSecret != nil {
		in, out := &in.ClientCertSecret, &out.ClientCertSecret
		*out = new(v1.SecretReference)
		**out = **in
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new AuthenticationServiceSettings.
func (in *AuthenticationServiceSettings) DeepCopy() *AuthenticationServiceSettings {
	if in == nil {
		return nil
	}
	out := new(AuthenticationServiceSettings)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *BrokerStatus) DeepCopyInto(out *BrokerStatus) {
	*out = *in
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new BrokerStatus.
func (in *BrokerStatus) DeepCopy() *BrokerStatus {
	if in == nil {
		return nil
	}
	out := new(BrokerStatus)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *CertificateSpec) DeepCopyInto(out *CertificateSpec) {
	*out = *in
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new CertificateSpec.
func (in *CertificateSpec) DeepCopy() *CertificateSpec {
	if in == nil {
		return nil
	}
	out := new(CertificateSpec)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *ConnectorAddressRule) DeepCopyInto(out *ConnectorAddressRule) {
	*out = *in
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new ConnectorAddressRule.
func (in *ConnectorAddressRule) DeepCopy() *ConnectorAddressRule {
	if in == nil {
		return nil
	}
	out := new(ConnectorAddressRule)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *ConnectorCredentialsSpec) DeepCopyInto(out *ConnectorCredentialsSpec) {
	*out = *in
	out.Username = in.Username
	out.Password = in.Password
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new ConnectorCredentialsSpec.
func (in *ConnectorCredentialsSpec) DeepCopy() *ConnectorCredentialsSpec {
	if in == nil {
		return nil
	}
	out := new(ConnectorCredentialsSpec)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *ConnectorEndpointHost) DeepCopyInto(out *ConnectorEndpointHost) {
	*out = *in
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new ConnectorEndpointHost.
func (in *ConnectorEndpointHost) DeepCopy() *ConnectorEndpointHost {
	if in == nil {
		return nil
	}
	out := new(ConnectorEndpointHost)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *ConnectorSpec) DeepCopyInto(out *ConnectorSpec) {
	*out = *in
	if in.EndpointHosts != nil {
		in, out := &in.EndpointHosts, &out.EndpointHosts
		*out = make([]ConnectorEndpointHost, len(*in))
		copy(*out, *in)
	}
	out.Tls = in.Tls
	out.Credentials = in.Credentials
	if in.Addresses != nil {
		in, out := &in.Addresses, &out.Addresses
		*out = make([]ConnectorAddressRule, len(*in))
		copy(*out, *in)
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new ConnectorSpec.
func (in *ConnectorSpec) DeepCopy() *ConnectorSpec {
	if in == nil {
		return nil
	}
	out := new(ConnectorSpec)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *ConnectorStatus) DeepCopyInto(out *ConnectorStatus) {
	*out = *in
	if in.Messages != nil {
		in, out := &in.Messages, &out.Messages
		*out = make([]string, len(*in))
		copy(*out, *in)
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new ConnectorStatus.
func (in *ConnectorStatus) DeepCopy() *ConnectorStatus {
	if in == nil {
		return nil
	}
	out := new(ConnectorStatus)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *ConnectorTlsSpec) DeepCopyInto(out *ConnectorTlsSpec) {
	*out = *in
	out.CaCert = in.CaCert
	out.ClientCert = in.ClientCert
	out.ClientKey = in.ClientKey
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new ConnectorTlsSpec.
func (in *ConnectorTlsSpec) DeepCopy() *ConnectorTlsSpec {
	if in == nil {
		return nil
	}
	out := new(ConnectorTlsSpec)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *EndpointSpec) DeepCopyInto(out *EndpointSpec) {
	*out = *in
	if in.Certificate != nil {
		in, out := &in.Certificate, &out.Certificate
		*out = new(CertificateSpec)
		**out = **in
	}
	if in.Expose != nil {
		in, out := &in.Expose, &out.Expose
		*out = new(ExposeSpec)
		**out = **in
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new EndpointSpec.
func (in *EndpointSpec) DeepCopy() *EndpointSpec {
	if in == nil {
		return nil
	}
	out := new(EndpointSpec)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *EndpointStatus) DeepCopyInto(out *EndpointStatus) {
	*out = *in
	if in.Certificate != nil {
		in, out := &in.Certificate, &out.Certificate
		*out = make([]byte, len(*in))
		copy(*out, *in)
	}
	if in.ServicePorts != nil {
		in, out := &in.ServicePorts, &out.ServicePorts
		*out = make([]Port, len(*in))
		copy(*out, *in)
	}
	if in.ExternalPorts != nil {
		in, out := &in.ExternalPorts, &out.ExternalPorts
		*out = make([]Port, len(*in))
		copy(*out, *in)
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new EndpointStatus.
func (in *EndpointStatus) DeepCopy() *EndpointStatus {
	if in == nil {
		return nil
	}
	out := new(EndpointStatus)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *ExposeSpec) DeepCopyInto(out *ExposeSpec) {
	*out = *in
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new ExposeSpec.
func (in *ExposeSpec) DeepCopy() *ExposeSpec {
	if in == nil {
		return nil
	}
	out := new(ExposeSpec)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *ForwarderSpec) DeepCopyInto(out *ForwarderSpec) {
	*out = *in
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new ForwarderSpec.
func (in *ForwarderSpec) DeepCopy() *ForwarderSpec {
	if in == nil {
		return nil
	}
	out := new(ForwarderSpec)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *ForwarderStatus) DeepCopyInto(out *ForwarderStatus) {
	*out = *in
	if in.Messages != nil {
		in, out := &in.Messages, &out.Messages
		*out = make([]string, len(*in))
		copy(*out, *in)
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new ForwarderStatus.
func (in *ForwarderStatus) DeepCopy() *ForwarderStatus {
	if in == nil {
		return nil
	}
	out := new(ForwarderStatus)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *ImageOverride) DeepCopyInto(out *ImageOverride) {
	*out = *in
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new ImageOverride.
func (in *ImageOverride) DeepCopy() *ImageOverride {
	if in == nil {
		return nil
	}
	out := new(ImageOverride)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *Port) DeepCopyInto(out *Port) {
	*out = *in
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new Port.
func (in *Port) DeepCopy() *Port {
	if in == nil {
		return nil
	}
	out := new(Port)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *RouterStatus) DeepCopyInto(out *RouterStatus) {
	*out = *in
	if in.Neighbours != nil {
		in, out := &in.Neighbours, &out.Neighbours
		*out = make([]string, len(*in))
		copy(*out, *in)
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new RouterStatus.
func (in *RouterStatus) DeepCopy() *RouterStatus {
	if in == nil {
		return nil
	}
	out := new(RouterStatus)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *SecretKeySelector) DeepCopyInto(out *SecretKeySelector) {
	*out = *in
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new SecretKeySelector.
func (in *SecretKeySelector) DeepCopy() *SecretKeySelector {
	if in == nil {
		return nil
	}
	out := new(SecretKeySelector)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *StringOrSecretSelector) DeepCopyInto(out *StringOrSecretSelector) {
	*out = *in
	out.ValueFromSecret = in.ValueFromSecret
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new StringOrSecretSelector.
func (in *StringOrSecretSelector) DeepCopy() *StringOrSecretSelector {
	if in == nil {
		return nil
	}
	out := new(StringOrSecretSelector)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *SubscriptionSpec) DeepCopyInto(out *SubscriptionSpec) {
	*out = *in
	if in.MaxConsumers != nil {
		in, out := &in.MaxConsumers, &out.MaxConsumers
		*out = new(int)
		**out = **in
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new SubscriptionSpec.
func (in *SubscriptionSpec) DeepCopy() *SubscriptionSpec {
	if in == nil {
		return nil
	}
	out := new(SubscriptionSpec)
	in.DeepCopyInto(out)
	return out
}

// DeepCopyInto is an autogenerated deepcopy function, copying the receiver, writing into out. in must be non-nil.
func (in *SubscriptionStatus) DeepCopyInto(out *SubscriptionStatus) {
	*out = *in
	if in.MaxConsumers != nil {
		in, out := &in.MaxConsumers, &out.MaxConsumers
		*out = new(int)
		**out = **in
	}
	return
}

// DeepCopy is an autogenerated deepcopy function, copying the receiver, creating a new SubscriptionStatus.
func (in *SubscriptionStatus) DeepCopy() *SubscriptionStatus {
	if in == nil {
		return nil
	}
	out := new(SubscriptionStatus)
	in.DeepCopyInto(out)
	return out
}
