/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.earldouglas.filtre;

import java.util.ArrayList;
import java.util.List;

public class AddressManager {

	private List<AddressBlock> allowList = new ArrayList<AddressBlock>();
	private List<AddressBlock> denyList = new ArrayList<AddressBlock>();

	public void extendAllowList(String compositeAllowList)
			throws AddressFormatException {
		for (String addressString : decomposeList(compositeAllowList)) {
			AddressBlock addressBlock = new AddressBlock(addressString);
			allowList.add(addressBlock);
		}
	}

	public void extendDenyList(String compositeDenyList)
			throws AddressFormatException {
		for (String addressString : decomposeList(compositeDenyList)) {
			AddressBlock addressBlock = new AddressBlock(addressString);
			denyList.add(addressBlock);
		}
	}

	public boolean isAccessPermitted(String address)
			throws AddressFormatException {

		if (contains(denyList, address)) {
			// Top priority. If address is denylisted, no other conditions can
			// allow it.
			return false;
		} else if (contains(allowList, address)) {
			// Second priority. If address is allowlisted and not denylisted,
			// allow it.
			return true;
		} else if ((allowList == null || allowList.size() == 0)) {
			// Fallback. If no addresses are allowlisted, assume no implied
			// restrictions.
			return true;
		} else {
			// Disallow by default.
			return false;
		}
	}

	private boolean contains(List<AddressBlock> addressBlockList,
			String addressString) throws AddressFormatException {
		long address = AddressCalculator.computeAddress(addressString);
		for (AddressBlock addressBlock : addressBlockList) {
			if (addressBlock.contains(address)) {
				return true;
			}
		}
		return false;
	}

	private String[] decomposeList(String compositeList) {
		if (compositeList != null) {
			return compositeList.split("[^\\d\\./]");
		} else {
			return new String[0];
		}
	}
}
