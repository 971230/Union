package zte.service;

import com.ztesoft.net.mall.core.service.IImportManager;

import zte.net.iservice.IImportService;

public class ImportService implements IImportService {

	private IImportManager importManager;
	
	public IImportManager getImportManager() {
		return importManager;
	}

	public void setImportManager(IImportManager importManager) {
		this.importManager = importManager;
	}

	@Override
	public void modifyMidDataStatus(String log_id,String id,String status,String desc) {
		importManager.modifyMidDataStatus(log_id, id, status, desc);
	}

}
