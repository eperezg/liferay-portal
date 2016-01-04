<#assign dlFileEntryTypeModel = dataFactory.defaultDLFileEntryTypeModel>

insert into DLFileEntryType values ('${dlFileEntryTypeModel.uuid}', ${dlFileEntryTypeModel.fileEntryTypeId}, ${dlFileEntryTypeModel.groupId}, ${dlFileEntryTypeModel.companyId}, ${dlFileEntryTypeModel.userId}, '${dlFileEntryTypeModel.userName}', '${dataFactory.getDateString(dlFileEntryTypeModel.createDate)}', '${dataFactory.getDateString(dlFileEntryTypeModel.modifiedDate)}', '${dlFileEntryTypeModel.fileEntryTypeKey}', '${dlFileEntryTypeModel.name}', '${dlFileEntryTypeModel.description}', '${dataFactory.getDateString(dlFileEntryTypeModel.lastPublishDate)}');

<#assign ddmStructureModel = dataFactory.defaultDLDDMStructureModel>

insert into DDMStructure values ('${ddmStructureModel.uuid}', ${ddmStructureModel.structureId}, ${ddmStructureModel.groupId}, ${ddmStructureModel.companyId}, ${ddmStructureModel.userId}, '${ddmStructureModel.userName}', ${ddmStructureModel.versionUserId}, '${ddmStructureModel.versionUserName}', '${dataFactory.getDateString(ddmStructureModel.createDate)}', '${dataFactory.getDateString(ddmStructureModel.modifiedDate)}', ${ddmStructureModel.parentStructureId}, ${ddmStructureModel.classNameId}, '${ddmStructureModel.structureKey}', '${ddmStructureModel.version}', '${ddmStructureModel.name}', '${ddmStructureModel.description}', '${ddmStructureModel.definition}', '${ddmStructureModel.storageType}', ${ddmStructureModel.type}, '${dataFactory.getDateString(ddmStructureModel.lastPublishDate)}');

<#assign ddmStructureLayoutModel = dataFactory.defaultDLDDMStructureLayoutModel>

insert into DDMStructureLayout values ('${ddmStructureLayoutModel.uuid}', ${ddmStructureLayoutModel.structureLayoutId}, ${ddmStructureLayoutModel.groupId}, ${ddmStructureLayoutModel.companyId}, ${ddmStructureLayoutModel.userId}, '${ddmStructureLayoutModel.userName}', '${dataFactory.getDateString(ddmStructureLayoutModel.createDate)}', '${dataFactory.getDateString(ddmStructureLayoutModel.modifiedDate)}', ${ddmStructureLayoutModel.structureVersionId},'${ddmStructureLayoutModel.definition}');

<#assign ddmStructureVersionModel = dataFactory.defaultDLDDMStructureVersionModel>

insert into DDMStructureVersion values (${ddmStructureVersionModel.structureVersionId}, ${ddmStructureVersionModel.groupId}, ${ddmStructureVersionModel.companyId}, ${ddmStructureVersionModel.userId}, '${ddmStructureVersionModel.userName}', '${dataFactory.getDateString(ddmStructureVersionModel.createDate)}',  ${ddmStructureVersionModel.structureId}, '${ddmStructureVersionModel.version}', ${ddmStructureVersionModel.parentStructureId}, '${ddmStructureVersionModel.name}', '${ddmStructureVersionModel.description}', '${ddmStructureVersionModel.definition}', '${ddmStructureVersionModel.storageType}', ${ddmStructureVersionModel.type}, ${ddmStructureVersionModel.status}, ${ddmStructureVersionModel.statusByUserId}, '${ddmStructureVersionModel.statusByUserName}', '${dataFactory.getDateString(ddmStructureVersionModel.statusDate)}');