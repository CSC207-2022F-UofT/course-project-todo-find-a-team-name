package recommend_br_use_case.application_business;

import recommend_br_use_case.application_business.BRBlockResponseModel;

import java.util.List;

/**
 * Class representing section entity, containing section code, and blocks of the section represented by
 * BRBlockResponseModel
 */
public class BRSectionResponseModel {

   private String code;
   private List<BRBlockResponseModel> brBlockResponseModels;

   // Rep invariant:
   //    startTimes.size() == endTimes.size() == days.size()

   /**
    * Construct BRSectionResponseModel given the section code and blocks
    * represented by BRBlockResponseModel
    *
    * @param code section code of the section represented by this class
    * @param brBlockResponseModels blocks of the section represented by this class
    */
   public BRSectionResponseModel(String code, List<BRBlockResponseModel> brBlockResponseModels){
      this.code = code;
      this.brBlockResponseModels = brBlockResponseModels;
   }

   /**
    * returns code of the section represented by this class
    *
    * @return code of the section represented by this class
    */
   public String getCode() {
      return code;
   }

   /**
    * returns blocks of the section represented by this class as a list of
    * BRBlockResponseModel
    *
    * @return blocks of the section represented by this class
    */
   public List<BRBlockResponseModel> getBrBlockResponseModels() {
      return brBlockResponseModels;
   }

   /**
    * Set code of the section represented by this class to the given code
    *
    * @param code new code of the section
    */
   public void setCode(String code) {
      this.code = code;
   }

   /**
    * Set blocks of the section represented by this class to the given code
    *
    * @param brBlockResponseModels new blocks of the section
    */
   public void setBrBlockResponseModels(List<BRBlockResponseModel> brBlockResponseModels) {
      this.brBlockResponseModels = brBlockResponseModels;
   }
}
