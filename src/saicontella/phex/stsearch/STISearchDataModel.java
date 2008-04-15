package saicontella.phex.stsearch;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import phex.gui.tabs.search.SearchResultElement;

/**
 *
 */
public interface STISearchDataModel
{
    /**
     * Sets the visualiztion model that needs to be updated on data model
     * changes or null if currently not visible.
     * @param model the new visualization model or null if not visible.
     */
    public void setVisualizationModel( STSearchTreeTableModel model );

    /**
     * @param index
     * @return
     */
    public SearchResultElement getSearchElementAt(int index);

    /**
     * @return
     */
    public int getSearchElementCount();

    /**
     * @param i
     * @param isSortedAscending
     */
    public void setSortBy(int i, boolean isSortedAscending);

}
