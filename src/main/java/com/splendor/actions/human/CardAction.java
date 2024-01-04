package com.splendor.actions.human;

import com.splendor.actions.HumanAction;
import com.splendor.constants.Messages;
import com.splendor.constants.Utility;


public abstract class CardAction extends HumanAction {

    public void displayAction() {
        Utility.display.out.println(Messages.POSITION_SELECTION);
        Utility.display.out.println(Messages.CARD_MESSAGE);
    }
}
