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

package com.liferay.journal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseService;

/**
 * Provides the remote service interface for JournalFolder. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see JournalFolderServiceUtil
 * @see com.liferay.journal.service.base.JournalFolderServiceBaseImpl
 * @see com.liferay.journal.service.impl.JournalFolderServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=journal", "json.web.service.context.path=JournalFolder"}, service = JournalFolderService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface JournalFolderService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JournalFolderServiceUtil} to access the journal folder remote service. Add custom service methods to {@link com.liferay.journal.service.impl.JournalFolderServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.journal.model.JournalFolder addFolder(long groupId,
		long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public void deleteFolder(long folderId) throws PortalException;

	public void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.journal.model.JournalFolder fetchFolder(long folderId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructure> getDDMStructures(
		long[] groupIds, long folderId, int restrictionType)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.journal.model.JournalFolder getFolder(long folderId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Long> getFolderIds(long groupId,
		long folderId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.journal.model.JournalFolder> getFolders(
		long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.journal.model.JournalFolder> getFolders(
		long groupId, long parentFolderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.journal.model.JournalFolder> getFolders(
		long groupId, long parentFolderId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.journal.model.JournalFolder> getFolders(
		long groupId, long parentFolderId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.journal.model.JournalFolder> getFolders(
		long groupId, long parentFolderId, int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object> getFoldersAndArticles(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object> getFoldersAndArticles(
		long groupId, long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object> getFoldersAndArticles(
		long groupId, long userId, long folderId, int status, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator<?> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersAndArticlesCount(long groupId, long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersAndArticlesCount(long groupId, long folderId,
		int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersAndArticlesCount(long groupId,
		java.util.List<java.lang.Long> folderIds, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersAndArticlesCount(long groupId, long userId,
		long folderId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersCount(long groupId, long parentFolderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersCount(long groupId, long parentFolderId, int status);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#getSubfolderIds(java.util.List, long, long, boolean)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId, boolean recurse);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Long> getSubfolderIds(long groupId,
		long folderId, boolean recurse);

	public com.liferay.journal.model.JournalFolder moveFolder(long folderId,
		long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.journal.model.JournalFolder moveFolderFromTrash(
		long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.journal.model.JournalFolder moveFolderToTrash(
		long folderId) throws PortalException;

	public void restoreFolderFromTrash(long folderId) throws PortalException;

	public void subscribe(long groupId, long folderId)
		throws PortalException;

	public void unsubscribe(long groupId, long folderId)
		throws PortalException;

	public com.liferay.journal.model.JournalFolder updateFolder(long groupId,
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, long[] ddmStructureIds,
		int restrictionType, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.journal.model.JournalFolder updateFolder(long groupId,
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;
}