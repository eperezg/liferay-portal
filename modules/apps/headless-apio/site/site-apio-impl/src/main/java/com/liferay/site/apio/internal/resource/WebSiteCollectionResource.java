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

package com.liferay.site.apio.internal.resource;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.person.apio.identifier.PersonIdentifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.site.apio.identifier.WebSiteIdentifier;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/WebSite">WebSite </a> resources through a web API.
 * The resources are mapped from the internal model {@link Group}.
 *
 * @author Victor Oliveira
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class WebSiteCollectionResource
	implements CollectionResource<Group, Long, WebSiteIdentifier> {

	@Override
	public CollectionRoutes<Group> collectionRoutes(
		CollectionRoutes.Builder<Group> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).build();
	}

	@Override
	public String getName() {
		return "web-sites";
	}

	@Override
	public ItemRoutes<Group, Long> itemRoutes(
		ItemRoutes.Builder<Group, Long> builder) {

		return builder.addGetter(
			_groupService::getGroup
		).build();
	}

	@Override
	public Representor<Group, Long> representor(
		Representor.Builder<Group, Long> builder) {

		/* TODO -> get url, GroupURLProvider.getGroupURL is not valid because
		request is not accesible*/

		return builder.types(
			"WebSite"
		).identifier(
			Group::getGroupId
		).addBidirectionalModel(
			"interactionService", "webSites", WebSiteIdentifier.class,
			this::_getParentGroupId
		).addLinkedModel(
			"author", PersonIdentifier.class, Group::getCreatorUserId
		).addLinkedModel(
			"creator", PersonIdentifier.class, Group::getCreatorUserId
		).addLocalizedStringByLocale(
			"active", this::_isActive
		).addLocalizedStringByLocale(
			"description", Group::getDescription
		).addLocalizedStringByLocale(
			"name", Group::getName
		).addLocalizedStringByLocale(
			"numberOfMembers", this::_numberOfMembersMessage
		).addRelatedCollection(
			"members", PersonIdentifier.class
		).addString(
			"membershipType", Group::getTypeLabel
		).build();
	}

	private PageItems<Group> _getPageItems(
			Pagination pagination, Company company)
		throws PortalException {

		int count = _groupService.getGroupsCount(
			company.getCompanyId(), 0, true);
		List<Group> groups = _groupService.getGroups(
			company.getCompanyId(), 0, true, pagination.getStartPosition(),
			pagination.getEndPosition());

		return new PageItems<>(groups, count);
	}

	private Long _getParentGroupId(Group group) {
		if (group.getParentGroupId() != 0L) {
			return group.getParentGroupId();
		}
		else {
			return null;
		}
	}

	private String _isActive(Group group, Locale locale) {
		return LanguageUtil.get(locale, group.isActive() ? "yes" : "no");
	}

	private String _numberOfMembersMessage(Group group, Locale locale) {
		return Try.fromFallible(
			() -> _userLocalService.getGroupUsersCount(
				group.getGroupId(), WorkflowConstants.STATUS_APPROVED)
		).map(
			count -> {
				if (count == 0) {
					return String.valueOf(count);
				}
				else {
					return LanguageUtil.format(
						locale, count > 1 ? "x-users" : "x-user", count);
				}
			}
		).orElse(
			null
		);
	}

	@Reference
	private GroupService _groupService;

	@Reference
	private UserLocalService _userLocalService;

}