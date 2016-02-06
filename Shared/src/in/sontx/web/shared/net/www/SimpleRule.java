package in.sontx.web.shared.net.www;

import java.util.HashMap;

public abstract class SimpleRule extends Rule {
	// task name | is anonymous?
	private final HashMap<String, Boolean> taskMap = new HashMap<String, Boolean>();

	protected abstract boolean isLogin(HttpServletRequestWrapper request);

	protected abstract String getTaskName(HttpServletRequestWrapper request);

	@Override
	public boolean executable(HttpServletRequestWrapper request) {
		boolean logged = isLogin(request);
		String req = getTaskName(request);
		return logged || anonymous(req);
	}

	protected boolean anonymous(String taskName) {
		if (taskName == null || taskName.length() == 0)
			return false;
		return taskMap.containsKey(taskName) ? taskMap.get(taskName) : false;
	}

	protected abstract void setupTaskMap(HashMap<String, Boolean> taskMap);

	public SimpleRule() {
		setupTaskMap(taskMap);
	}
}
