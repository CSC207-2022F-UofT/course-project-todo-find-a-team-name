package overlap_crap_fix_locations_later;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.Flow;

public interface OverlapInputFunctionality extends Flow.Publisher {

    // Support some flavour of cancelling the input dialog..
    abstract void setUpCancelFunctionality();

    // Support some flavour of passing input back to the Interactor.
    abstract void setUpInputPassing();

    // Support some flavour of creation. with a timetable list
    abstract JDialog createWithScheduleList(ArrayList<String> timetablesToChooseFrom);
}
