package GameView;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class RulesView extends GridPane{
	
	public RulesView() {
		
	setMinSize(175, 375);
        setGridLinesVisible(false);
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-border-color: white; -fx-border-width: 0 1 1 1; -fx-background-color: #9ac6d6; -fx-fill: #DAA520; fx-font-weight: bold;");
        Text controlsHeader = new Text("Controls");
        controlsHeader.setStyle("-fx-fill: red; -fx-font-size: 12pt");
        Text controls = new Text("Movement: \nleft and right \narrow keys \nRotation: \nup and down \narrow keys \nDrop piece:\nspacebar \n");
        Text rulesHeader = new Text("Rules");
        rulesHeader.setStyle("-fx-fill: red; -fx-font-size: 12pt");
        Text rule = new Text("Earn score by \nclearing lines\nwhen the board \nfills up\nyou lose.\n1 line:  100\n2 lines: 300\n3 lines: 600 \n4 lines: 1000\n5 lines: 1500 ");
        
        add(controlsHeader, 0,1);
        add(controls, 0, 2);
        add(rulesHeader, 0 ,3);
        add(rule, 0, 4);
        setHalignment(controlsHeader, HPos.CENTER);
        setHalignment(controls, HPos.CENTER);
        setHalignment(rulesHeader, HPos.CENTER);
        setHalignment(rule, HPos.CENTER);
	}
	
}
