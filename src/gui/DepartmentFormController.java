package gui;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exception.ValidationException;
import service.model.DepartmentService;

public class DepartmentFormController implements Initializable {
	
	List<DataChangeListener> dataChangeListeners = new ArrayList<>();
    
	private Department entity;
	
	private DepartmentService service;
	
	 @FXML
	 private TextField txtId;
	 
	 @FXML
	 private TextField txtName;
	 
	 @FXML
	 private Button btSave;
	 
	 @FXML
	 private Button btCancel;
	 
	 @FXML
	 private Label labelErrorName;
	 
	 @FXML
	 public void onBtSaveAction(ActionEvent event) {
		 if(entity == null) {
			 throw new IllegalStateException("service was null");
		 }
		 if(service == null) {
			 throw new IllegalStateException("service was null");
		 }
		 try {
		  entity = getFormData();
		  service.saveOrUpdate(entity);
		  notifyListeners();
		  Utils.currentStage(event).close();
		 }
		 catch(ValidationException e) {
			 setErrorMessages(e.getErrors());
		 }
		 catch(DbException e) {
			 Alerts.showAlert("Error saving obj", null, e.getMessage(), AlertType.ERROR);
		 }
	}
	 
	 private void notifyListeners() {
		for(DataChangeListener list :dataChangeListeners) {
			list.onDataChange();
		}
		
	}

	private Department getFormData() {
		 Department obj = new Department();
		 
		 ValidationException exception = new ValidationException("validation exception");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if(txtName.getText() == null || txtName.getText().trim().equals("") ) {
		   exception.addError("name", "field can`t be empty");
		}
		obj.setName(txtName.getText());
		
		if(exception.getErrors().size() > 0 ) {
			throw exception;
		}
		return obj;
		
	}

	@FXML
	 public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	 }
	 
	 @Override
		public void initialize(URL uri, ResourceBundle rb) {
	
		}
	 
	 public void setDepartment(Department entity) {
		 this.entity = entity;
	 }
	 
	 public void setDepartmentService(DepartmentService service) {
		 this.service = service;
	 }
	 
	 public void updateFormData() {
		 if(entity == null) {
			 throw new IllegalStateException("Entity was null");
		 }
		 txtId.setText(String.valueOf(entity.getId()));
		 txtName.setText(entity.getName());
	 }
	 
	 public void addListener(DataChangeListener listener) {
		 dataChangeListeners.add(listener);
	 }
	 
	 public void setErrorMessages(Map<String,String> errors) {
		  Set<String> fields = errors.keySet();
		  
		  if(fields.contains("name")) {
			  labelErrorName.setText(errors.get("name"));
		  }
	 }
		

		
	

}
