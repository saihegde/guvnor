/*
 * Copyright 2012 JBoss Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kie.guvnor.dtablexls.backend.server.conversion.builders;

import org.drools.decisiontable.parser.ActionType;
import org.drools.decisiontable.parser.RuleSheetParserUtil;
import org.drools.guvnor.models.guided.dtable.shared.conversion.ConversionResult;
import org.drools.guvnor.models.guided.dtable.shared.model.AttributeCol52;
import org.drools.guvnor.models.guided.dtable.shared.model.DTCellValue52;
import org.drools.guvnor.models.guided.dtable.shared.model.GuidedDecisionTable52;

/**
 * Builder for AutoFocus Attribute columns
 */
public class GuidedDecisionTableAutoFocusBuilder extends AbstractGuidedDecisionTableAttributeBuilder {

    public GuidedDecisionTableAutoFocusBuilder( final int row,
                                                final int column,
                                                final ConversionResult conversionResult ) {
        super( row,
               column,
               ActionType.Code.AUTOFOCUS,
               conversionResult );
    }

    public void populateDecisionTable( final GuidedDecisionTable52 dtable ) {
        final AttributeCol52 column = new AttributeCol52();
        column.setAttribute( GuidedDecisionTable52.AUTO_FOCUS_ATTR );
        dtable.getAttributeCols().add( column );
        addColumnData( dtable,
                       column );
    }

    public void addCellValue( int row,
                              int column,
                              String value ) {
        final DTCellValue52 dcv = new DTCellValue52( RuleSheetParserUtil.isStringMeaningTrue( value ) );
        this.values.add( dcv );
    }

}
