package blacklist_whitelist_use_case;

import blacklist_whitelist_use_case.application_business.SectionFilterInputBoundary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterController;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * TestCases for SectionFilterController
 * Test whether the Controller passes the request model successfully to the interactor.
 * The main logic for filtering occurs inside SectionFilterInteractor and Constraints Entity.
 * The tests can be found in:
 *                test/java/
 *                  blacklist_whitelist_use_case/
 *                      SectionFilterInteractorTest
 *                  entities/
 *                      InstructorConstraintTest
 *                      TimeIntervalConstraintTest
 *                      WeekdayConstraintTest
 *                      RoomConstraintTest
 */
class SectionFilterControllerTest {
    SectionFilterController sectionFilterController;

    @Test
    void filter() {
        SectionFilterInputBoundary interactor = requestModel -> {
            String expectedCourseCodes = "CSC207H1, CSC258H1, CSC236H1, MAT235H1";
            String isInstructorBlackList = "BLACKLIST";
            String isRoomBlackList = "BLACKLIST";
            String isDayBlackList = "BLACKLIST";
            String isTimeBlackList = "BLACKLIST";
            String expectedInstructorConstraints = "W Zhan";
            String expectedRoomConstraints = "SS 990";
            ArrayList<Integer> expectedDays = new ArrayList<>(Arrays.asList(0,1,2,3));
            String expectedStartTime = "7:30";
            String expectedEndTime = "8:30";
            Assertions.assertEquals(expectedCourseCodes, requestModel.getCourseCodes());
            Assertions.assertEquals(isInstructorBlackList, requestModel.getIsInstructorBlackList());
            Assertions.assertEquals(isDayBlackList, requestModel.getIsDayBlackList());
            Assertions.assertEquals(isRoomBlackList, requestModel.getIsRoomBlackList());
            Assertions.assertEquals(isTimeBlackList, requestModel.getIsTimeBlackList());
            Assertions.assertEquals(expectedInstructorConstraints, requestModel.getInstructorConstraints());
            Assertions.assertEquals(expectedRoomConstraints, requestModel.getRoomConstraints());
            Assertions.assertEquals(expectedStartTime, requestModel.getStartTime());
            Assertions.assertEquals(expectedEndTime, requestModel.getEndTime());
            Assertions.assertEquals(expectedDays, requestModel.getDayConstraints());
        };
        sectionFilterController = new SectionFilterController(interactor);
        sectionFilterController.filter(
                "CSC207H1, CSC258H1, CSC236H1, MAT235H1",
                "BLACKLIST",
                "BLACKLIST",
                "BLACKLIST",
                "BLACKLIST",
                "W Zhan",
                "SS 990",
                new ArrayList<>(Arrays.asList(0,1,2,3)),
                "7:30",
                "8:30");
    }
}