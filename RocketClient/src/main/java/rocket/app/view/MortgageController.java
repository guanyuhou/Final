package rocket.app.view;



import eNums.eAction;
import exceptions.RateException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import rocket.app.MainApp;
import rocketBase.RateBLL;
import rocketCode.Action;
import rocketData.LoanRequest;

public class MortgageController {

	private MainApp mainApp;

	// TODO - RocketClient.RocketMainController

	// Create private instance variables for:
	// TextBox - txtIncome
	// TextBox - txtExpenses
	// TextBox - txtCreditScore
	// TextBox - txtHouseCost
	// ComboBox - loan term... 15 year or 30 year
	// Labels - various labels for the controls
	// Button - button to calculate the loan payment
	// Label - to show error messages (exception throw, payment exception)

	@FXML
	private TextField txtIncome;
	@FXML
	private TextField txtExpenses;
	@FXML
	private TextField txtCreditScore;
	@FXML
	private TextField txtHouseCost;	
	@FXML
	private TextField txtdownPayment;
	@FXML
	private ComboBox<String> cmbTerm;
	@FXML
	private Button loanCalculation;
	@FXML
	private Label ibiMortgagePaymentLabel;
	@FXML
	private Label incomeLabel;
	@FXML
	private Label creditScoreLabel;
	@FXML
	private Label termLabel;
	@FXML
	private Label houseCostLabel;
	@FXML
	private Label expensesLabel;
	@FXML
	public Label errorMessageLabel;
	@FXML
	private Label interestRate;

	public int term;

	ObservableList<String> termList = FXCollections.observableArrayList("15 Years", "30 Years");

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void initialize() {
		cmbTerm.setItems(termList);
	}

	// assign the value of terms
	@FXML
	private void termChoice() {
		if (cmbTerm.getValue().equals("15 Years")) {
			term = 15 * 12;
		} else {
			term = 30 * 12;
		}
	}

	// TODO - RocketClient.RocketMainController
	// Call this when btnPayment is pressed, calculate the payment
	@FXML
	public void btnCalculatePayment(ActionEvent event) throws RateException {
		Action a = new Action(eAction.CalculatePayment);
		LoanRequest lq = new LoanRequest();
		// TODO - RocketClient.RocketMainController
		// set the loan request details... rate, term, amount, credit score,
		// down payment
		// I've created you an instance of lq... execute the setters in lq

		int creditScore = Integer.parseInt(txtCreditScore.getText());
		double rate = RateBLL.getRate(creditScore);
		double amount = Double.parseDouble(txtHouseCost.getText());
		double downPayment = Double.parseDouble(txtdownPayment.getText());

		// set rate
		lq.setdRate(rate);

		// set term
		lq.setiTerm(term);

		// set amount
		lq.setdAmount(amount);

		// set credit score
		lq.setiCreditScore(creditScore);

		// set iDownPayment
		lq.setiDownPayment((int) downPayment);

		a.setLoanRequest(lq);

		// send lq as a message to RocketHub
		mainApp.messageSend(lq);
		
		// clear the table
		errorMessageLabel.setText("");
		ibiMortgagePaymentLabel.setText("");
		interestRate.setText("");
		
	}

	public void HandleLoanRequestDetails(LoanRequest lRequest) {
		// TODO - RocketClient.HandleLoanRequestDetails
		// lRequest is an instance of LoanRequest.
		// after it's returned back from the server, the payment (dPayment)
		// should be calculated.
		// Display dPayment on the form, rounded to two decimal places

		double income = Double.parseDouble(txtIncome.getText());
		double expenses = Double.parseDouble(txtExpenses.getText());
				
		double roundedPMT = (Math.round(lRequest.getdPayment()*100.0)/100.0);		
		double Rate = lRequest.getdRate();
		if (roundedPMT >= income * 0.28 || roundedPMT >= (income * 0.36 - expenses)) {
			errorMessageLabel.setText("House Cost too high");
		} else {
			String textPMT = Double.toString(roundedPMT);
			ibiMortgagePaymentLabel.setText("$ "+textPMT);
			
			String textRate = Double.toString(Rate);
			interestRate.setText(textRate+"%");
		}

	}
}