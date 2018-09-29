package controller;

import entity.Inventory;
import entity.MFVConstants;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * This class contains functions that can be used to generate reports.
 *
 * @author W0\/E|\| L|_|
 */
public class ReporterController {

    /**
     * Finds all stocked items in the inventory, i.e ones that can be bought.
     * @param inventory The inventory object to get items from.
     * @return A list of ID's that meet the specified conditions.
     */
    public List<UUID> getStockedItems(Inventory inventory){
        return inventory.findItemsByState(MFVConstants.STOCKED);
    }

    /**
     * Finds all donated items within specified dates.
     * @param inventory inventory The inventory object to get items from.
     * @param earliestDate Earliest item for which product life ended to include.
     * @param latestDate Latest item for which product life ended to include.
     * @return A list of ID's that meet the specified conditions.
     */
    public List<UUID> getDonatedItems(Inventory inventory, Calendar earliestDate, Calendar latestDate){
        return inventory.findItemsByStateAndDate(earliestDate, latestDate, MFVConstants.CHARITY);
    }

    /**
     * Finds all discarded items within specified dates.
     * @param inventory inventory The inventory object to get items from.
     * @param earliestDate Earliest item for which product life ended to include.
     * @param latestDate Latest item for which product life ended to include.
     * @return A list of ID's that meet the specified conditions.
     */
    public List<UUID> getDiscardedItems(Inventory inventory, Calendar earliestDate, Calendar latestDate){
        return inventory.findItemsByStateAndDate(earliestDate, latestDate, MFVConstants.DISCARDED);
    }


}
