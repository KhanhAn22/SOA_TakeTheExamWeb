package ctu.cit.anchaunhut.Handle;

import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URI;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class AdminService {

	private void convertListQuizToHTML(PrintWriter out, String listQuizString) {
		convertListQuizToHTMLService(out, listQuizString);
	}

	public void readAllQuiz(PrintWriter out) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		URI uri = UriBuilder.fromUri("http://localhost:8080/Quiz/api/quiz/readAllQuiz").build();

		WebTarget target = client.target(uri);

		String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);

//		System.out.println("AdminService.readAllQuiz: Start out.println() data:");
		// Call API convert String List QUIZ to HTML
		convertListQuizToHTML(out, response);
	}

	// Received list of Quiz ( contain Quiz_id ) - will get quiz_id, then call api
	// for each quiz, then call convertQuizToHTML
	private void convertListQuizToHTMLService(PrintWriter out, String listQuizString) {

		// Convert dataQuiz ( contain question + option )
		JsonReader jsonReader = Json.createReader(new StringReader(listQuizString));
		JsonArray quizArray = jsonReader.readArray();

		for (int i = 0; i < quizArray.size(); i++) {
			JsonObject quizObj = quizArray.getJsonObject(i);

			String quiz_id = quizObj.getString("quiz_id");
			String quiz_title = quizObj.getString("quiz_title");
			String quiz_description = quizObj.getString("quiz_description");
			String creator_id = quizObj.getString("creator_id");
			String created_at = quizObj.getString("created_at");

			//// Start <div> contain each Quiz
			out.println("<div id=\"quiz" + quiz_id
					+ "\" style='border: 1px solid black; padding: 10px; margin-bottom: 10px;'>");

			// Output quiz data======================================================
			out.println("<p><strong>Title:</strong> " + quiz_title + "</p>");
			out.println("<p><strong>Description:</strong> " + quiz_description + "</p>");
			out.println("<p><strong>Creator ID:</strong> " + creator_id + "</p>");
			out.println("<p><strong>Created At:</strong> " + created_at + "</p>");
			// Output quiz data======================================================

			// Buttons==================================
			out.println("<div style='text-align: right;'>");

			out.println("<button onclick=\"showQuizCollection('" + quiz_id + "')\">Show</button>");
			out.println("<button onclick=\"showHideDeleteQuestion('buttonDeleteQuestion" + quiz_id + "')\">Delete Questions</button>");
			out.println("<button onclick=\"deleteQuiz('quiz" + quiz_id + "')\">Delete This Quiz</button>");

			out.println(" </div>");
			// Buttons==================================

			// Quiz ( Question + Option
			// )==================================================================
			out.println("<div id=\"" + quiz_id + "\" style=\"display:none;\">");

			String idFormAddNewQuestion = "addNewQuestion" + quiz_id;
			out.println("<a class=\"button\" onclick=addNewQuestion('" + idFormAddNewQuestion
					+ "') style=\"margin-left:10%;\">Add New Question</a><br><br>");

			// Form Add new Question
			formAddNewQuestion(out, quiz_id);
			
			// Call API get Quiz + out.println all information of Quiz.
			// Try get Quiz ( Full question and option of each question. )
			try {
				System.out.println("Start Call API get Quiz by ID:" + quiz_id);
				getQuizById(out, quiz_id);
			} catch (Exception e) {
				System.out.println(e);
				out.println("AdminService.convertListQuizToHTMLService : Goi thong tin Quiz theo Id bi loi");
			}

			out.println("</div>");
			// Quiz ( Question + Option
			// )==================================================================

			// Close <div> contain each Quiz
			out.println("</div>");

		}
	}

	private void formAddNewQuestion(PrintWriter out, String quiz_id) {
		out.println("<form id=\"addNewQuestion" + quiz_id
				+ "\" action=\"/UI_Quiz/handle_addNewQuestion\" style=\"display:none;\" method=\"POST\">");
		out.println("    <input type=\"hidden\" id=\"quiz_id\" name=\"quiz_id\" value=\"" + quiz_id + "\">");
		out.println("");
		out.println("    <label for=\"question_text\">Question:</label><br>");
		out.println("    <input type=\"text\" id=\"question_text\" name=\"question_text\" required><br><br>");
		out.println("");
		out.println("    <label for=\"option_text1\">Option 1:</label><br>");
		out.println("    <input type=\"text\" id=\"option_text1\" name=\"option_text1\" required><br><br>");
		out.println("");
		out.println("    <label for=\"option_text2\">Option 2:</label><br>");
		out.println("    <input type=\"text\" id=\"option_text2\" name=\"option_text2\" required><br><br>");
		out.println("");
		out.println("    <label for=\"option_text3\">Option 3:</label><br>");
		out.println("    <input type=\"text\" id=\"option_text3\" name=\"option_text3\" required><br><br>");
		out.println("");
		out.println("    <label for=\"option_text4\">Option 4:</label><br>");
		out.println("    <input type=\"text\" id=\"option_text4\" name=\"option_text4\" required><br><br>");
		out.println("");
		out.println("    <label for=\"options\">Correct Answer ?</label><br>");

		out.println(
				"<select id=\"options\"  name=\"is_correct\">\r\n" + "		  <option value=\"1\">Option 1</option>\r\n"
						+ "		  <option value=\"2\">Option 2</option>\r\n"
						+ "		  <option value=\"3\">Option 3</option>\r\n"
						+ "		  <option value=\"4\">Option 4</option>\r\n" + "		</select>");
		out.println("");
		out.println("    <input type=\"submit\" value=\"Submit\">");
		out.println("</form>");
	}

//	private void formDeleteQuestion(PrintWriter out, String quiz_id) {
//		out.println("<br><form id=\"deleteQuestion" + quiz_id + "\" style=\"display: none;\">");
//		out.println("    <label for=\"question_text\">Number Question Delete :</label><br>");
//		out.println("    <input type=\"text\" id=\"question_text\" >");
//		out.println("    <input type=\"submit\" value=\"Submit\">");
//
//		out.println("</form>");
//	}

	// Call API read Quiz with quiz_id is parameter
	private void getQuizById(PrintWriter out, String quiz_id) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		URI uri = UriBuilder.fromUri("http://localhost:8080/Quiz/api/quiz/read").build();

		WebTarget target = client.target(uri);

		String response = target.queryParam("quiz_id", quiz_id).request().accept(MediaType.APPLICATION_JSON)
				.get(String.class);

		// Start out.println() data Question to UI
		System.out.println("AdminService.getQuizById - Start convert data Quiz to HTML");

		convertQuizToHTML(out, response);
	}

	private void convertQuizToHTML(PrintWriter out, String dataQuiz) {

		JsonReader jsonReader = Json.createReader(new StringReader(dataQuiz));
		JsonObject quizArray = jsonReader.readObject();

		String quiz_id = quizArray.getString("quiz_id");

		JsonArray questionArray = quizArray.getJsonArray("question");

		// Get Question
		int countNumberQuestion = 1;
		for (int i = 0; i < questionArray.size(); i++) {
			JsonObject questionObject = questionArray.getJsonObject(i);

			String question_id = questionObject.getString("question_id");
			String question_text = questionObject.getString("question_text");

			// out.println information of Question

			// Start div contain question + all Options
			out.println("<div id=\"question" + question_id + "\">");

			// Start div contain question_name + button delete
			// question===============================
			out.println("<div style=\"display:ruby;\">");

			out.println("<h4>Question  " + countNumberQuestion++ + ": " + question_text + "</h4>");
			out.println("<button class=\"buttonDeleteQuestion" + quiz_id + "\" style=\"display:none;\" onclick=\"deleteQuestionWithId('" + question_id
					+ "')\">Delete This Question</button>");

			// End div contain question_name + button delete
			// question===============================
			out.println("</div>");

			JsonArray optionArray = questionObject.getJsonArray("options");

			// Get option
			int countNumberOption = 1;
			for (int j = 0; j < optionArray.size(); j++) {

				JsonObject optionObject = optionArray.getJsonObject(j);

				String option_text = optionObject.getString("option_text");
				Boolean is_correct = optionObject.getBoolean("is_correct");

				// out.println information of Question
				if (is_correct) {
					out.println("<p class=\"option\" style=\"background-color: green\">");
					out.println("Option " + countNumberOption++ + ": " + option_text);
					out.println(" => Correct answer");
					out.println("</p>");
				} else {
					out.println("<p  class=\"option\">");
					out.println("Option " + countNumberOption++ + ": " + option_text);
					out.println("</p>");
				}

			}
			// End div contain question + all Options
			out.println("</div>");
		}

	}

	public void cssDashboardAdmin(PrintWriter out) {
		out.println("<style>");

		out.println(".button {\r\n" + "  display: inline-block;\r\n" + "  padding: 10px 20px;\r\n"
				+ "  background-color: #007bff; /* Blue color */\r\n" + "  color: white;\r\n"
				+ "  text-decoration: none;\r\n" + "  border-radius: 5px;\r\n" + "}\r\n" + "\r\n"
				+ ".button:hover {\r\n" + "  background-color: #0056b3; /* Darker blue color on hover */\r\n" + "}");
		// Css for Option indented
		out.println(".option {\r\n" + "    margin-left: 20px;"
				+ "width: fit-content; /* Adjust the value to change the indentation */\r\n" + "}");
		out.println("</style>");
	}

	public void scriptDashboardAdmin(PrintWriter out) {
		out.println("<script>");

		// Hide and Unhide addNewQuestion
		out.println("function addNewQuestion(idFormAddNewQuestion) {\r\n"
				+ "    var addNewQuestionForm = document.getElementById(idFormAddNewQuestion);\r\n" + "\r\n"
				+ "    // Get the computed style of the form\r\n"
				+ "    var computedStyle = window.getComputedStyle(addNewQuestionForm);\r\n" + "\r\n"
				+ "    // Check if the form is currently hidden\r\n"
				+ "    if (computedStyle.display === \"none\") {\r\n" + "        // Unhide the form\r\n"
				+ "        addNewQuestionForm.style.display = \"block\";\r\n" + "    } else {\r\n"
				+ "        // Hide the form\r\n" + "        addNewQuestionForm.style.display = \"none\";\r\n"
				+ "    }\r\n" + "}");

		// hide and unhide deleteQuestion========================
		out.println("function deleteQuestion(quizFormDiv) {\r\n" + "    // Get the div with id \"quizFormDiv\"\r\n"
				+ "    var quizFormDiv = document.getElementById(quizFormDiv);\r\n" + "\r\n"
				+ "    // Check if the div is hidden\r\n" + "    if (quizFormDiv.style.display === \"none\") {\r\n"
				+ "        quizFormDiv.style.display = \"block\";\r\n" + "      } else {\r\n"
				+ "        quizFormDiv.style.display = \"none\";\r\n" + "      }" + "}");

		// hide and Unhide form addNewQuiz
		out.println("function addNewQuiz() {\r\n" + "    // Get the div with id \"quizFormDiv\"\r\n"
				+ "    var quizFormDiv = document.getElementById(\"quizFormDiv\");\r\n" + "\r\n"
				+ "    // Check if the div is hidden\r\n" + "    if (quizFormDiv.style.display === \"none\") {\r\n"
				+ "        quizFormDiv.style.display = \"block\";\r\n" + "      } else {\r\n"
				+ "        quizFormDiv.style.display = \"none\";\r\n" + "      }" + "}");
		// Hide and unHide Quiz
		out.println("function showQuizCollection(quiz_id) {\r\n"
				+ "        var div = document.getElementById(quiz_id);\r\n"
				+ "        if (div.style.display === \"none\") {\r\n" + "            div.style.display = \"block\";\r\n"
				+ "        } else {\r\n" + "            div.style.display = \"none\";\r\n" + "        }\r\n" + "    }");
		// Delete Quiz function
		out.println("function deleteQuiz(quiz_id) {\r\n"
				+ "      // Extract the numeric part from the quiz_id parameter\r\n"
				+ "      var quizIdNumber = quiz_id.match(/\\d+/)[0];\r\n" + "\r\n"
				+ "      if (confirm('Are you sure you want to delete this quiz?')) {\r\n"
				+ "        const url = 'http://localhost:8080/Quiz/api/quiz/delete?quiz_id=' + quizIdNumber;\r\n"
				+ "        console.log(\"url : \" + url);\r\n" + "\r\n" + "        fetch(url, {\r\n"
				+ "          method: 'POST',\r\n" + "          headers: {\r\n"
				+ "            'Content-Type': 'application/json'\r\n" + "          },\r\n"
				+ "          body: JSON.stringify({ quiz_id: quizIdNumber })\r\n" + "        })\r\n"
				+ "          .then(response => {\r\n" + "            if (!response.ok) {\r\n"
				+ "              throw new Error('Network response was not ok');\r\n" + "            }\r\n"
				+ "            return response.text();\r\n" + "          })\r\n" + "          .then(data => {\r\n"
				+ "            console.log('Data received:', data);\r\n"
				+ "            var div = document.getElementById(quiz_id);\r\n"
				+ "            if (div.style.display === \"none\") {\r\n"
				+ "              div.style.display = \"block\";\r\n" + "            } else {\r\n"
				+ "              div.style.display = \"none\";\r\n" + "            }\r\n"
				+ "            if (data.trim() === \"DELETE_Quiz_SUCCESS\") {\r\n"
				+ "              alert(\"DELETE SUCCESS\");\r\n" + "            }\r\n" + "          })\r\n"
				+ "          .catch(error => {\r\n" + "            console.error('Fetch error:', error);\r\n"
				+ "          });\r\n" + "      } else {\r\n" + "        return;\r\n" + "      }\r\n" + "    }");

		// Delete Question function
		out.println("function deleteQuestionWithId(question_id) {");
		out.println("      var question_idNumber = \"question_id\" + \"=\" + question_id;");
		out.println("			 const divContainQuestion = \"question\" + question_id;");
		out.println("");
		out.println("      if (confirm('Are you sure you want to delete this Question?')) {");
		out.println("        const url = 'http://localhost:8080/Questions/api/questions/delete?' + question_idNumber;");
		out.println("        console.log(\"url : \" + url);");
		out.println("        console.log(\"divContainQuestion : \" + divContainQuestion);");
		out.println("");
		out.println("        fetch(url, {");
		out.println("          method: 'POST',");
		out.println("          headers: {");
		out.println("            'Content-Type': 'application/json'");
		out.println("          },");
		out.println("          body: JSON.stringify({ question_id: question_id })");
		out.println("        })");
		out.println("          .then(response => {");
		out.println("            if (!response.ok) {");
		out.println("              throw new Error('Network response was not ok');");
		out.println("            }");
		out.println("            return response.text();");
		out.println("          })");
		out.println("          .then(data => {");
		out.println("            console.log('Data received:', data);");
		out.println("            var div = document.getElementById(divContainQuestion);");
		out.println("            if (div.style.display === \"none\") {");
		out.println("              div.style.display = \"block\";");
		out.println("            } else {");
		out.println("              div.style.display = \"none\";");
		out.println("            }");
		out.println("            if (data.trim() === \"Delete \\\"QUESTION\\\" Success \") {");
		out.println("              alert(\"DELETE Question SUCCESS\");");
		out.println("            }");
		out.println("          })");
		out.println("          .catch(error => {");
		out.println("            console.error('Fetch error:', error);");
		out.println("          });");
		out.println("      } else {");
		out.println("        return;");
		out.println("      }");
		out.println("    }");
		
		// Hide and unhide showHideDeleteQuestion
		out.println("function showHideDeleteQuestion(buttonDeleteQuestionId) {");
		out.println("    var elements = document.getElementsByClassName(buttonDeleteQuestionId);");
		out.println("");
		out.println("    // Iterate over the elements");
		out.println("    for (var i = 0; i < elements.length; i++) {");
		out.println("        // Toggle the display property");
		out.println("        if (elements[i].style.display === \"none\") {");
		out.println("            elements[i].style.display = \"block\";");
		out.println("        } else {");
		out.println("            elements[i].style.display = \"none\";");
		out.println("        }");
		out.println("    }");
		out.println("}");
		out.println("</script>");

	}

}
