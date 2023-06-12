/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package commint_lint_jira;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import commint_lint_jira.dto.CommitLintJiraRestModel;

/**
 * A simple 'hello world' plugin.
 */
public class CommitLintJiraPlugin implements Plugin<Project> {
    public void apply(Project project) {
        // Register a task
       project.getExtensions().create("commitlintPlugin",CommitLintJiraRestModel.class);
    }


		// project.getTasks().create("commitlint",CommitLintJiraRestModel.class, task -> {
    //         task.doLast(s -> {
		// 					File file = new File(System.getProperty("msgfile", ".git/COMMIT_EDITMSG"));

		// 					task.getConfiguration()
		// 					CommitLintJiraRestModel data = new CommitLintJiraRestModel("","","","");
		// 					CommitLintJiraRest rest = new CommitLintJiraRest(data);
		// 					try {
		// 						String msg = readFile(file);
		// 						// "PTSCA-1085 prueba lugares";

		// 					} catch (IOException e) {
		// 						throw e;
		// 					}
		// 					// try {
		// 					// 	util.validate(msg, extension.enforceRefs.getOrElse(false))
		// 					// } catch (InvalidUserDataException e) {
		// 					// 	throw new InvalidUserDataException(util.addANSIColor(e.getMessage(), 31))
		// 					// }
		// 					// println("commitlint finished successfully")
		// 				});
    //     });

}