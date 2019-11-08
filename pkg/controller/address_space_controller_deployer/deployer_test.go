/*
 * Copyright 2019, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package address_space_controller_deployment

import (
	"fmt"
	"testing"

	"k8s.io/client-go/kubernetes/fake"
	"k8s.io/client-go/kubernetes/scheme"
)

func TestPerformDeployment(t *testing.T) {
	client := fake.NewSimpleClientSet()
	d := &AddressSpaceCOntrollerDeployment{
		client: client,
		config: nil,
		scheme: scheme.Scheme,
		"test",
	}

	err := d.ensureDeployment()
	if err != nil {
		t.Error(fmt.Sprintf("Deployment was not ok"))
	}
}
