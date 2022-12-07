package recommend_br_use_case.interface_adapters;

import org.junit.jupiter.api.Test;
import recommend_br_use_case.application_business.RecommendBRInputBoundary;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class used for testing RecommendBRController
 */
class RecommendBRControllerTest {

    /**
     * Checks whether RecommendBRController is converting the input to request model correctly and
     * passing that to RecommendBRInputBoundary
     */
    @Test
    void recommendBr() {
        RecommendBRInputBoundary dummyInteractor = requestModel -> {
            HashSet<String> expectedBrCategoriesSelected = new HashSet<>();
            expectedBrCategoriesSelected.add("1");
            expectedBrCategoriesSelected.add("4");
            String expectedPreferredTime = "don't care";

            assertEquals(expectedBrCategoriesSelected, requestModel.getBrCategoriesSelected());
            assertEquals(expectedPreferredTime, requestModel.getPreferredTime());
        };

        RecommendBRController controller = new RecommendBRController(dummyInteractor);
        HashSet<String> brCategoriesSelected = new HashSet<>();
        brCategoriesSelected.add("1");
        brCategoriesSelected.add("4");
        controller.recommendBr(brCategoriesSelected, "don't care");

    }
}