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

package com.liferay.forms.apio.internal.architect.resource;

import static com.liferay.forms.apio.internal.util.LocalizedValueUtil.getLocalizedString;
import static com.liferay.forms.apio.internal.util.StructureRepresentorUtil.getFieldOptions;
import static com.liferay.forms.apio.internal.util.StructureRepresentorUtil.getFieldProperty;
import static com.liferay.forms.apio.internal.util.StructureRepresentorUtil.hasFormRules;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.NestedRepresentor;
import com.liferay.apio.architect.representor.NestedRepresentor.Builder;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormSuccessPageSettings;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.forms.apio.architect.identifier.StructureIdentifier;
import com.liferay.forms.apio.internal.model.FormLayoutPage;
import com.liferay.forms.apio.internal.util.StructureRepresentorUtil;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.ClassNameService;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.List;
import java.util.Map.Entry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose Structure resources through a
 * web API. The resources are mapped from the internal model {@code
 * DDMStructure}.
 *
 * @author Paulo Cruz
 */
@Component
public class StructureNestedCollectionResource
	implements
		NestedCollectionResource<DDMStructure, Long, StructureIdentifier,
			Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<DDMStructure, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<DDMStructure, Long, Long> builder) {

		return builder.addGetter(this::_getPageItems, Company.class).build();
	}

	@Override
	public String getName() {
		return "structure";
	}

	@Override
	public ItemRoutes<DDMStructure, Long> itemRoutes(
		ItemRoutes.Builder<DDMStructure, Long> builder) {

		return builder.addGetter(
			_ddmStructureService::getStructure
		).build();
	}

	@Override
	public Representor<DDMStructure> representor(
		Representor.Builder<DDMStructure, Long> builder) {

		return builder.types(
			"Structure"
		).identifier(
			DDMStructure::getStructureId
		).addBidirectionalModel(
			"interactionService", "structures", WebSiteIdentifier.class,
			DDMStructure::getGroupId
		).addDate(
			"dateCreated", DDMStructure::getCreateDate
		).addDate(
			"dateModified", DDMStructure::getCreateDate
		).addDate(
			"datePublished", DDMStructure::getLastPublishDate
		).addLinkedModel(
			"author", PersonIdentifier.class, DDMStructure::getUserId
		).addLocalizedStringByLocale(
			"description", DDMStructure::getDescription
		).addLocalizedStringByLocale(
			"name", DDMStructure::getName
		).addNested(
			"successPage", StructureRepresentorUtil::getSuccessPage,
			StructureNestedCollectionResource::_buildSuccessPageSettings
		).addNested(
			"version", StructureRepresentorUtil::getVersion,
			StructureNestedCollectionResource::_buildVersion
		).addNestedList(
			"pages", StructureRepresentorUtil::getPages,
			StructureNestedCollectionResource::_buildFormPages
		).build();
	}

	private static NestedRepresentor<Entry<String, LocalizedValue>>
		_buildFieldOptions(Builder<Entry<String, LocalizedValue>> builder) {

		return builder.types(
			"FormFieldOptions"
		).addLocalizedStringByLocale(
			"label", getLocalizedString(Entry::getValue)
		).addString(
			"value", Entry::getKey
		).build();
	}

	private static NestedRepresentor<DDMFormField> _buildFormFields(
		Builder<DDMFormField> builder) {

		return builder.types(
			"FormField"
		).addBoolean(
			"hasFormRules", hasFormRules()
		).addBoolean(
			"isAutocomplete",
			getFieldProperty(Boolean.class::cast, "autocomplete")
		).addBoolean(
			"isInline", getFieldProperty(Boolean.class::cast, "inline")
		).addBoolean(
			"isLocalizable", DDMFormField::isLocalizable
		).addBoolean(
			"isMultiple", DDMFormField::isMultiple
		).addBoolean(
			"isReadOnly", DDMFormField::isReadOnly
		).addBoolean(
			"isRepeatable", DDMFormField::isRepeatable
		).addBoolean(
			"isRequired", DDMFormField::isRequired
		).addBoolean(
			"isShowAsSwitcher",
			getFieldProperty(Boolean.class::cast, "showAsSwitcher")
		).addBoolean(
			"isShowLabel", DDMFormField::isShowLabel
		).addBoolean(
			"isTransient", DDMFormField::isTransient
		).addLocalizedStringByLocale(
			"label", getLocalizedString(DDMFormField::getLabel)
		).addLocalizedStringByLocale(
			"placeholder",
			StructureRepresentorUtil.getLocalizedString("placeholder")
		).addLocalizedStringByLocale(
			"predefinedValue",
			getLocalizedString(DDMFormField::getPredefinedValue)
		).addLocalizedStringByLocale(
			"style", getLocalizedString(DDMFormField::getStyle)
		).addLocalizedStringByLocale(
			"tip", getLocalizedString(DDMFormField::getTip)
		).addNested(
			"grid", ddmFormField -> ddmFormField,
			StructureNestedCollectionResource::_buildGridProperties
		).addNested(
			"validation", DDMFormField::getDDMFormFieldValidation,
			StructureNestedCollectionResource::_buildValidationProperties
		).addNestedList(
			"options", getFieldOptions(DDMFormField::getDDMFormFieldOptions),
			StructureNestedCollectionResource::_buildFieldOptions
		).addString(
			"additionalType", DDMFormField::getType
		).addString(
			"dataSourceType",
			getFieldProperty(String.class::cast, "dataSourceType")
		).addString(
			"dataType", DDMFormField::getDataType
		).addString(
			"displayStyle", getFieldProperty(String.class::cast, "displayStyle")
		).addString(
			"indexType", DDMFormField::getIndexType
		).addString(
			"name", DDMFormField::getName
		).addString(
			"text", getFieldProperty(String.class::cast, "text")
		).build();
	}

	private static NestedRepresentor<FormLayoutPage> _buildFormPages(
		Builder<FormLayoutPage> builder) {

		return builder.types(
			"FormLayoutPage"
		).addLocalizedStringByLocale(
			"headline", FormLayoutPage::getTitle
		).addLocalizedStringByLocale(
			"text", FormLayoutPage::getDescription
		).addNestedList(
			"fields", FormLayoutPage::getFields,
			StructureNestedCollectionResource::_buildFormFields
		).build();
	}

	private static NestedRepresentor<DDMFormField> _buildGridProperties(
		Builder<DDMFormField> builder) {

		return builder.types(
			"FormFieldProperties"
		).addNestedList(
			"columns", getFieldOptions("columns"),
			StructureNestedCollectionResource::_buildFieldOptions
		).addNestedList(
			"rows", getFieldOptions("rows"),
			StructureNestedCollectionResource::_buildFieldOptions
		).build();
	}

	private static NestedRepresentor<DDMFormSuccessPageSettings>
		_buildSuccessPageSettings(Builder<DDMFormSuccessPageSettings> builder) {

		return builder.types(
			"FormSuccessPageSettings"
		).addBoolean(
			"isEnabled", DDMFormSuccessPageSettings::isEnabled
		).addLocalizedStringByLocale(
			"headline", getLocalizedString(DDMFormSuccessPageSettings::getTitle)
		).addLocalizedStringByLocale(
			"text", getLocalizedString(DDMFormSuccessPageSettings::getBody)
		).build();
	}

	private static NestedRepresentor<DDMFormFieldValidation>
		_buildValidationProperties(Builder<DDMFormFieldValidation> builder) {

		return builder.types(
			"FormFieldProperties"
		).addString(
			"error", DDMFormFieldValidation::getErrorMessage
		).addString(
			"expression", DDMFormFieldValidation::getExpression
		).build();
	}

	private static NestedRepresentor<DDMStructureVersion> _buildVersion(
		Builder<DDMStructureVersion> nestedBuilder) {

		return nestedBuilder.types(
			"StructureVersion"
		).addLinkedModel(
			"author", PersonIdentifier.class, DDMStructureVersion::getUserId
		).addString(
			"name", DDMStructureVersion::getVersion
		).build();
	}

	private PageItems<DDMStructure> _getPageItems(
		Pagination pagination, Long aLong, Company o) {

		ClassName className = _classNameService.fetchClassName(
			DDLRecordSet.class.getName());

		Long classNameId = className.getClassNameId();

		List<DDMStructure> structures = _ddmStructureService.getStructures(
			o.getCompanyId(), new long[] {aLong}, classNameId, 0,
			pagination.getStartPosition(), pagination.getEndPosition(), null);

		int structuresCount = _ddmStructureLocalService.getStructuresCount(
			aLong, classNameId);

		return new PageItems<>(structures, structuresCount);
	}

	@Reference
	private ClassNameService _classNameService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureService _ddmStructureService;

}