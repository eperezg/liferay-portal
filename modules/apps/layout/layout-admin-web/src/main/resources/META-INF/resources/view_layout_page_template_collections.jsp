<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
List<LayoutPageTemplateCollection> layoutPageTemplateCollections = LayoutPageTemplateCollectionServiceUtil.getLayoutPageTemplateCollections(themeDisplay.getScopeGroupId());

LayoutPageTemplateDisplayContext layoutPageTemplateDisplayContext = new LayoutPageTemplateDisplayContext(renderRequest, renderResponse, request);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= layoutsAdminDisplayContext.getNavigationItems() %>"
/>

<div class="container-fluid container-fluid-max-xl container-view">
	<div class="row">
		<nav class="menubar menubar-transparent menubar-vertical-expand-lg">
			<ul class="nav nav-nested">
				<li class="nav-item">
					<c:choose>
						<c:when test="<%= ListUtil.isNotEmpty(layoutPageTemplateCollections) %>">
							<strong class="text-uppercase">
								<liferay-ui:message key="collections" />
							</strong>

							<ul class="nav nav-stacked">

								<%
								for (LayoutPageTemplateCollection layoutPageTemplateCollection : layoutPageTemplateCollections) {
								%>

									<li class="nav-item">

										<%
										PortletURL layoutPageTemplateCollectionURL = renderResponse.createRenderURL();

										layoutPageTemplateCollectionURL.setParameter("layoutPageTemplateCollectionId", String.valueOf(layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()));
										layoutPageTemplateCollectionURL.setParameter("tabs1", "page-templates");
										%>

										<a class="nav-link truncate-text <%= (layoutPageTemplateCollection.getLayoutPageTemplateCollectionId() == layoutPageTemplateDisplayContext.getLayoutPageTemplateCollectionId()) ? "active" : StringPool.BLANK %>" href="<%= layoutPageTemplateCollectionURL.toString() %>">
											<%= layoutPageTemplateCollection.getName() %>
										</a>
									</li>

								<%
								}
								%>

							</ul>
						</c:when>
						<c:otherwise>
							<p class="text-uppercase">
								<strong><liferay-ui:message key="collections" /></strong>
							</p>

							<h2 class="text-center">
								<liferay-ui:message key="no-collections-yet" />
							</h2>

							<p class="text-center">
								<liferay-ui:message key="collections-are-needed-to-create-page-templates" />
							</p>

							<portlet:renderURL var="editLayoutPageTemplateCollectionURL">
								<portlet:param name="mvcPath" value="/edit_layout_page_template_collection.jsp" />
							</portlet:renderURL>

							<aui:a cssClass="btn btn-primary" href="<%= editLayoutPageTemplateCollectionURL %>" label="add-collection" />
						</c:otherwise>
					</c:choose>
				</li>
			</ul>
		</nav>
	</div>
</div>