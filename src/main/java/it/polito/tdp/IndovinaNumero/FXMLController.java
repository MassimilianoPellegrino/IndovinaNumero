/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.IndovinaNumero;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import it.polito.tdp.invovinanumero.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class FXMLController {

	private Model model;
	
	private int NMAX;
	private int TMAX;
	private LinkedList<Integer> inseriti = new LinkedList<Integer>();
	private int iMin;
	private int iMax;
	private int segreto;
	private int tentativiFatti;
	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML
    private Label lblDiffScelta;
    
    @FXML
    private Label lblIntervallo;
    
    @FXML
    private Label lblModalita;
    
    @FXML
    private ProgressBar progress;
    
    @FXML // fx:id="btnNuovaPartita"
    private Button btnNuovaPartita; // Value injected by FXMLLoader

    @FXML // fx:id="txtTentativi"
    private TextField txtTentativi; // Value injected by FXMLLoader

    @FXML // fx:id="txtTentativoUtente"
    private TextField txtTentativoUtente; // Value injected by FXMLLoader

    @FXML // fx:id="btnProva"
    private Button btnProva; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader

    @FXML // fx:id="layoutTentativo"
    private HBox layoutTentativo; // Value injected by FXMLLoader
    
   @FXML
    void btnDifficile(ActionEvent event) {
	   this.NMAX = 1000;
	   this.TMAX = (int) (Math.log(NMAX)/Math.log(2));
	   if(!this.lblModalita.getText().equals(""))
		   this.btnNuovaPartita.setDisable(false);
	   this.lblDiffScelta.setText("Difficile");
    }

    @FXML
    void btnFacile(ActionEvent event) {
    	this.NMAX = 10;
    	this.TMAX = (int) (Math.log(NMAX)/Math.log(2));
    	if(!this.lblModalita.getText().equals(""))
    		this.btnNuovaPartita.setDisable(false);
    	this.lblDiffScelta.setText("Facile");
    }

    @FXML
    void btnMedia(ActionEvent event) {
    	this.NMAX = 100;
    	this.TMAX = (int) (Math.log(NMAX)/Math.log(2));
    	if(!this.lblModalita.getText().equals(""))
    		this.btnNuovaPartita.setDisable(false);
    	this.lblDiffScelta.setText("Media");
    }
    
    @FXML
    void doAssistita(ActionEvent event) {
    	if(!this.lblDiffScelta.getText().equals(""))
    		this.btnNuovaPartita.setDisable(false);
    	this.lblModalita.setText("Assistita");
    }

    @FXML
    void doNormale(ActionEvent event) {
    	if(!this.lblDiffScelta.getText().equals(""))
    		this.btnNuovaPartita.setDisable(false);
    	this.lblModalita.setText("Normale");
    }
    
    @FXML
    void doNuovaPartita(ActionEvent event) {
    	//gestione inizio nuova partita
    	this.inseriti.clear();
    	this.iMin=0;
    	this.iMax=NMAX;
    	this.segreto = (int) ((Math.random()*NMAX)+1);
    	this.tentativiFatti = 0;
    	//gestione dell'interfaccia
    	this.txtTentativi.setText(Integer.toString(TMAX));
    	this.txtRisultato.clear();
    	this.txtTentativoUtente.clear();
    	this.layoutTentativo.setDisable(false);
    }

    public int ritornaMax(LinkedList<Integer> list) {
    	int max = 0;
    	int last = list.getLast();
    	for(Integer i: list)
    		if(i>max && i<last)
    			max=i;
    	
    	if(max==0)
    		return max;
    	else
    		return max+1;
    }
    public int ritornaMin(LinkedList<Integer> list) {
    	int min = NMAX;
    	int last = list.getLast();
    	for(Integer i: list) 
    		if(i<min && i>last)
    			min=i;
    	
    	if(min==NMAX)
    		return min;
    	else
    		return min-1;
    }
    public boolean giaInserito(int num) {
    	for(Integer i: inseriti)
    		if(i==num)
    			return true;
    	return false;
    }
    
    
    @FXML
    void doTentativo(ActionEvent event) {
    	//lettura input dell'utente
    	String ts = txtTentativoUtente.getText();
    	
    	int tentativo;
    	try {
    		tentativo = Integer.parseInt(ts); 
    	}catch(NumberFormatException e) {
    		txtRisultato.setText("Devi inserire un numero!");
    		return;
    	}
    	
    	if(giaInserito(tentativo)) {
    		this.lblIntervallo.setText("Numero già inserito, inserire un numero tra "+iMin+" e "+iMax+" (compresi)");
    		return;
    	}		
    	if(tentativo<iMin || tentativo>iMax) {
    		this.lblIntervallo.setText("Fuori intervallo, inserire un numero tra "+iMin+" e "+iMax+" (compresi)");
    		return;
    	}
    	
    	inseriti.add(tentativo);
    	
    	if(tentativiFatti<TMAX) {
    		this.tentativiFatti++;
    		this.txtTentativi.setText(Integer.toString(TMAX-this.tentativiFatti));
    		this.progress.setProgress(tentativiFatti/(TMAX*1.0));
    	}
    	if(tentativo == this.segreto) {
    		//HO INDOVINATO
    		this.lblIntervallo.setText("");
    		txtRisultato.setText("HAI VINTO CON "+this.tentativiFatti+" TENTATIVI");
    		this.layoutTentativo.setDisable(true);
    		return;
    	}
    	
    	if(this.tentativiFatti == TMAX) {
    		//ho esaurito i tentativi
    		this.lblIntervallo.setText("");
    		txtRisultato.setText("HAI PERSO, IL SEGRETO ERA: "+this.segreto);
    		this.layoutTentativo.setDisable(true);
    		return;
    	}
    	
    	//Non ho vinto --> devo informare l'utente circa la bontà del suo tentativo
    	if(tentativo<this.segreto) {
    		txtRisultato.setText("TENTATIVO TROPPO BASSO");
    		if(this.lblModalita.getText().equals("Assistita")) {
    			if(this.inseriti.size()==1) {
    				this.iMin=tentativo+1;
    				this.lblIntervallo.setText("Il risultato si trova tra "+iMin+" e "+NMAX+" (compresi)");
    			}else {
    				this.iMin=tentativo+1;
    				this.iMax=this.ritornaMin(inseriti);
    				this.lblIntervallo.setText("Il risultato si trova tra "+iMin+" e "+iMax+" (compresi)");
    			}
    		}
    	}else {
    		txtRisultato.setText("TENTATIVO TROPPO ALTO");
    		if(this.lblModalita.getText().equals("Assistita")) {
    			if(this.inseriti.size()==1) {
    				this.iMax=tentativo-1;
    				this.lblIntervallo.setText("Il risultato si trova tra 0 e "+iMax+" (compresi)");
    			}else {
    				this.iMin=this.ritornaMax(inseriti);
    				this.iMax=tentativo-1;
    				this.lblIntervallo.setText("Il risultato si trova tra "+iMin+" e "+iMax+" (compresi)");
    			}
    		}
    	}
    		
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnNuovaPartita != null : "fx:id=\"btnNuovaPartita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTentativi != null : "fx:id=\"txtTentativi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTentativoUtente != null : "fx:id=\"txtTentativoUtente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnProva != null : "fx:id=\"btnProva\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
