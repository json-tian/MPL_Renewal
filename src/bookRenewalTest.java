import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class bookRenewalTest {

	private static JFrame frame;
	private static JButton startButton;
	private static JPasswordField barcodeField;
	private static JPasswordField passwordField;

	private static JLabel barcodeLabel;
	private static JLabel passwordLabel;
	private static JLabel titleLabel;

	public static void main(String[] args) {
		frame = new JFrame("MPL Auto Renewal");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(800, 600);
		frame.setLayout(null);

		Font smallFont = new Font("Monospace", Font.PLAIN, 20);
		Font largeFont = new Font("Arial", Font.PLAIN, 40);
		
		startButton = new JButton("Renew Items");
		startButton.setBounds(275, 400, 250, 75); //xCoord, yCoord, xSize, ySize
		startButton.setFont(smallFont);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Code to run when button is pressed
				frame.dispose();
				try {
					bookRenewal(String.copyValueOf(barcodeField.getPassword()), String.copyValueOf(passwordField.getPassword()));
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		frame.add(startButton);

		barcodeField = new JPasswordField();
		barcodeField.setBounds(175, 175, 450, 50);
		barcodeField.setEchoChar((char) 0);
		barcodeField.setFont(smallFont);
		frame.add(barcodeField);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(175, 300, 450, 50);
		passwordField.setFont(smallFont);
		frame.add(passwordField);

		barcodeLabel = new JLabel("Enter Barcode:");
		barcodeLabel.setBounds(175, 125, 250, 50);
		barcodeLabel.setFont(smallFont);
		frame.add(barcodeLabel);
		
		passwordLabel = new JLabel("Enter Password:");
		passwordLabel.setBounds(175, 250, 250, 50);
		passwordLabel.setFont(smallFont);
		frame.add(passwordLabel);
		
		titleLabel = new JLabel("MPL Auto Renewal");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setBounds(175, 0, 450, 100);
		titleLabel.setFont(largeFont);
		frame.add(titleLabel);
		
		frame.setVisible(true);
	}
	
	public static void bookRenewal(String barcode, String password) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "C:\\SeleniumDrivers\\chromedriver.exe"); //FOR TESTING
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://markham.bibliocommons.com/v2/checkedout");

		driver.findElement(By.xpath("//input[@class='field_username text']")).sendKeys(barcode);
		driver.findElement(By.xpath("//input[@class='text']")).sendKeys(password);

		driver.findElement(By.xpath("//input[@class='rememberMe']")).click();
		
		//Click login button
		driver.findElement(By.xpath("//input[@testid='button_login']")).click();
		
		//Renewing books
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@aria-labelledby='label_batch-actions-master']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@class='cp-btn btn cp-ghost-btn cp-dropdown-trigger actions-dropdown']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[@class='cp-batch-action-link']")).click();
	}
}
