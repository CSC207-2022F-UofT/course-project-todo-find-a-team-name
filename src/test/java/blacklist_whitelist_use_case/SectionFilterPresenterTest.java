package blacklist_whitelist_use_case;

import blacklist_whitelist_use_case.application_business.SectionFilterResponseModel;
import blacklist_whitelist_use_case.interface_adapters.ISectionFilterView;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterPresenter;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Cases for SectionFilterPresenter.
 * Test the formatting correctness, as well as passing the view Model to the view.
 * For testing purpose, Customized interface is used.
 */
class SectionFilterPresenterTest {
    private SectionFilterPresenter presenter;

    /**
     * Set up the presenter and view (from Customized IView for testing)
     */
    @BeforeEach
    void setUp(){
        ISectionFilterView view = new ISectionFilterView() {
            @Override
            public void showSuccessView(SectionFilterViewModel viewModel) {
                HashMap<String, List<String>> expected = new HashMap<>();
                expected.put("CSC207H1", new ArrayList<>(Arrays.asList("LEC890", "LEC898", "PRA110", "TUT777")));
                expected.put("CSC258H1", new ArrayList<>(Arrays.asList("LEC890", "LEC898", "PRA110", "TUT777", "TUT999")));
                assertEquals(expected, viewModel.getModifiedCourses());
            }
            @Override
            public void showFailView(String message) {
                assertEquals("Test Error Message", message);
            }
        };
        presenter = new SectionFilterPresenter();
        presenter.setView(view);
    }

    /**
     * Test the correctness of presenter formatting the responseModel into viewModel, and pass the viewModel to view.
     */
    @Test
    void prepareSuccessView() {
        HashMap<String, List<String>> modifiedCourses = new HashMap<>();
        modifiedCourses.put("CSC207H1", new ArrayList<>(Arrays.asList("PRA110", "LEC898", "TUT777", "LEC890")));
        modifiedCourses.put("CSC258H1", new ArrayList<>(Arrays.asList("PRA110", "LEC898", "TUT999", "TUT777", "LEC890")));
        SectionFilterResponseModel responseModel = new SectionFilterResponseModel(modifiedCourses);
        presenter.prepareSuccessView(responseModel);
    }

    /**
     * Test if the prepareFailView method would call the showFailView of the view.
     */
    @Test
    void prepareFailView() {
        presenter.prepareFailView("Test Error Message");
    }

}