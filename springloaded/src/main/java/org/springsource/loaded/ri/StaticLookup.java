/*
 * Copyright 2010-2012 VMware and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springsource.loaded.ri;

import java.util.List;

/**
 * Provides an implementation for dynamic method lookup in a given Method provider.
 * 
 * @author Kris De Volder
 * @since 0.5.0
 */
public class StaticLookup {

	private String name;

	private String methodDescriptor;

	/**
	 * Create an object capable of performing a dynamic method lookup in some MethodProvider
	 * 
	 * @param name the method name
	 * @param methodDescriptor the method descriptor (e.g. (Ljava/lang/String;)I)
	 */
	public StaticLookup(String name, String methodDescriptor) {
		this.name = name;
		this.methodDescriptor = methodDescriptor;
	}

	public Invoker lookup(MethodProvider methodProvider) {
		List<Invoker> methods = methodProvider.getDeclaredMethods();
		for (Invoker invoker : methods) {
			if (matches(invoker)) {
				return invoker;
			}
		}
		//Code below unreachable, because 'deleted' methods are checked for
		//before the method lookup.
		return null;
	}

	protected boolean matches(Invoker invoker) {
		return name.equals(invoker.getName()) && methodDescriptor.equals(invoker.getMethodDescriptor());
	}

}
