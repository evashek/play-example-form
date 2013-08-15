package controllers;

import assemblies.StudentFormData;
import models.GradeLevel;
import models.GradePointAverage;
import models.Hobby;
import models.Major;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

/**
 * The controller for the single page of this application.
 *
 * @author Philip Johnson
 */
public class Application extends Controller {

  /**
   * Returns the page where the form is filled by the Student whose id is passed, or an empty form
   * if the id is 0.
   * @param id The id of the Student whose data is to be shown.  0 if an empty form is to be shown.
   * @return The page containing the form and data.
   */
  public static Result getIndex(long id) {
    StudentFormData student = (id == 0) ? new StudentFormData() : models.Student.makeStudentFormData(id);
    Form<StudentFormData> form = Form.form(StudentFormData.class);
    return ok(index.render(
      form,
      Hobby.makeHobbyMap(student),
      GradeLevel.getNameList(),
      GradePointAverage.makeGPAMap(student),
      Major.makeMajorMap(student)
    ));
  }

  /**
   * Process a form submission.
   * First we bind the HTTP POST data to an instance of StudentFormData.
   * The binding process will invoke the StudentFormData.validate() method.
   * If errors are found, re-render the page, displaying the error data. 
   * If errors not found, render the page with the good data. 
   * @return The index page with the results of validation. 
   */
  public static Result postIndex() {

    Form<StudentFormData> form = Form.form(StudentFormData.class);
    // Retrieve the submitted form data from the request object.
    Form<StudentFormData> bound = form.bindFromRequest();

    if (bound.hasErrors()) {
      // Don't call bound.get() if there are any errors. 
      return badRequest(index.render(bound,
        Hobby.makeHobbyMap(null), 
        GradeLevel.getNameList(),
        GradePointAverage.makeGPAMap(null), 
        Major.makeMajorMap(null) 
      ));
    }
    else {
      return ok(index.render(bound,
        Hobby.makeHobbyMap(bound.get()),
        GradeLevel.getNameList(),
        GradePointAverage.makeGPAMap(bound.get()),
        Major.makeMajorMap(bound.get())
      ));
    }
  }
}
