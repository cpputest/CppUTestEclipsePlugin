package org.cpputest.plugin.SWTBotTest;

import static org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable.syncExec;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsInstanceOf;
import static org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory.withPartName;

public class CppProjectTestBase {
	SWTWorkbenchBot bot = new SWTWorkbenchBot();
	IWorkspace workspace = ResourcesPlugin.getWorkspace();
	IWorkspaceRoot root = workspace.getRoot();

	public CppProjectTestBase() {
		super();
	}

	protected void createCppProject(String ProjectName) throws CoreException {
		IProject project = root.getProject(ProjectName);
		project.create(new NullProgressMonitor());
		project.open(new NullProgressMonitor());
	}

	protected void deleteProject(String projectName) throws CoreException {
		root.getProject(projectName).delete(true, true, new NullProgressMonitor());
	}

	protected String getClipboardContent() {
		final StringBuilder content = new StringBuilder();
	
		syncExec(new VoidResult() {
			public void run() {
				Clipboard clipboard = new Clipboard(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell().getDisplay());
				TextTransfer textTransfer = TextTransfer.getInstance();
				content.append((String) clipboard.getContents(textTransfer));
			}
		});
		return content.toString();
	}
	
	protected void clickContextMenu(final SWTBotEclipseEditor editor, String string) {
		// this doesn't work so far.
		syncExec(new VoidResult() {
			public void run() {
				editor.toTextEditor().contextMenu("Copy Empty Stub To Clipboard").click();
			}
		});
		
	}

	@SuppressWarnings("unchecked")
	protected SWTBotEclipseEditor createNewCppFile(String projectName, String fileName, String content) throws CoreException {
		IProject project = root.getProject(projectName);
		IFile file = project.getFile(fileName);
		file.create(new ByteArrayInputStream(content.getBytes()), true, new NullProgressMonitor());
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		bot.viewByTitle("Project Explorer").bot().tree().expandNode(projectName).getNode(fileName).doubleClick();
		long oldTimeout = SWTBotPreferences.TIMEOUT;
		SWTBotPreferences.TIMEOUT = 5000;
		Matcher<?> editorMatcher = Matchers.allOf(
				IsInstanceOf.instanceOf(IEditorReference.class),
				withPartName(fileName)
				);
		bot.waitUntil(Conditions.waitForEditor((Matcher<IEditorReference>) editorMatcher));
		SWTBotPreferences.TIMEOUT = oldTimeout;
		SWTBotEditor swtBoteditor = bot.activeEditor();
		return swtBoteditor.toTextEditor();				
	}

}