/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.person.apio.internal.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.person.apio.identifier.PersonIdentifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleModel;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.role.apio.identifier.RoleIdentifier;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/Role">Role </a> resources through a web API. The
 * resources are mapped from the internal model {@code Role}.
 *
 * @author Javier Gamarra
 */
@Component(immediate = true)
public class RoleCollectionResource
	implements CollectionResource<Role, Long, RoleIdentifier> {

	@Override
	public CollectionRoutes<Role> collectionRoutes(
		CollectionRoutes.Builder<Role> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).build();
	}

	@Override
	public String getName() {
		return "roles";
	}

	@Override
	public ItemRoutes<Role, Long> itemRoutes(
		ItemRoutes.Builder<Role, Long> builder) {

		return builder.addGetter(
			roleIdentifier -> _roleService.getRole(roleIdentifier)
		).build();
	}

	@Override
	public Representor<Role> representor(
		Representor.Builder<Role, Long> builder) {

		return builder.types(
			"Role"
		).identifier(
			Role::getRoleId
		).addDate(
			"dateCreated", Role::getCreateDate
		).addDate(
			"dateModified", Role::getModifiedDate
		).addLinkedModel(
			"creator", PersonIdentifier.class, RoleModel::getUserId
		).addLocalizedStringByLocale(
			"description", Role::getDescription
		).addString(
			"name", Role::getName
		).build();
	}

	private PageItems<Role> _getPageItems(
			Pagination pagination, Company company)
		throws PortalException {

		int[] types = new int[0];
		List<Role> roles = _roleService.getRoles(company.getCompanyId(), types);

		return new PageItems<>(roles, roles.size());
	}

	@Reference
	private RoleService _roleService;

}