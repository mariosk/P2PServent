package saicontella.phex.stsearch.filterpanel;

import javax.swing.table.AbstractTableModel;

import phex.query.QueryManager;
import phex.rules.Rule;
import phex.rules.SearchFilterRules;
import phex.utils.Localizer;
import saicontella.phex.stsearch.STSearchResultsDataModel;

public class STFilterListTableModel extends AbstractTableModel
{
    private STFilterListPanel filterListPanel;
    private SearchFilterRules rules;
    private STSearchResultsDataModel displayedDataModel;
    
    public STFilterListTableModel( STFilterListPanel panel, SearchFilterRules rules )
    {
        filterListPanel = panel;
        this.rules = rules;
    }
    
    public void setDisplayedSearch( STSearchResultsDataModel dataModel )
    {
        // otherwise no need to update...
        if ( displayedDataModel != dataModel )
        {
            displayedDataModel = dataModel;
            fireTableDataChanged( );
        }
    }

    public int getColumnCount()
    {
        return 2;
    }

    public int getRowCount()
    {
        return rules.getCount();
    }

    public Object getValueAt( int rowIndex, int columnIndex )
    {
        Rule rowRule = rules.getRuleAt(rowIndex);
        switch ( columnIndex )
        {
        case 0:
            if ( displayedDataModel == null )
            {
                return Boolean.FALSE;
            }
            return displayedDataModel.isRuleActive( rowRule ) ? Boolean.TRUE : Boolean.FALSE;
        case 1:
            return rules.getRuleAt(rowIndex);
        }
        return "";
    }

    public String getColumnName( int column )
    {
        switch ( column )
        {
        case 1:
            return Localizer.getString("SearchTab_SelectRulesToActivate");
        }
        return " ";
    }

    public Class getColumnClass( int col )
    {
        switch ( col )
        {
        case 0:
            return Boolean.class;
        case 1:
            return Rule.class;
        }
        return Object.class;
    }

    public boolean isCellEditable( int row, int col )
    {
        return (col == 0) && displayedDataModel != null;
    }

    public void setValueAt( Object aValue, int row, int column )
    {
        Boolean boolVal = (Boolean)aValue;
        Rule rowRule = rules.getRuleAt( row );
        if ( boolVal.booleanValue() )
        {
            displayedDataModel.activateRule( rowRule );
        }
        else
        {
            displayedDataModel.deactivateRule( rowRule );
        }
    }
}
