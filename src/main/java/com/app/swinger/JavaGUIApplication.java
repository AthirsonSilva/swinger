package com.app.swinger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

//Como temos eventos, a classe herda de jFrame E implementa a ActionListener
public class JavaGUIApplication extends JFrame implements ActionListener {

	JButton btnAdd, btnClear;
	//Atributos locais da classe:
	private JPanel panelFields, panelTable, panelButtons, panelName, panelPhone, panelCity;
	private TitledBorder titlePnlFields, titlePnlTable, titlePnlButtons;
	private JLabel labelName, labelPhone, labelCity;
	private JTextField txtName;
	private JFormattedTextField txtPhone;
	private MaskFormatter mskPhone;
	private JTable tableClient;
	private DefaultTableModel tableClientModel;
	private JComboBox cmbCities;
	private DefaultComboBoxModel cmbCitiesModel;

	//Construtor, que coloca tudo no lugar
	public JavaGUIApplication() {
		defineWindow(); //Chama o método que seta as propriedades da janela
		panelFields = new JPanel(new GridLayout(3, 1)); //Um painel para os campos
		titlePnlFields = BorderFactory.createTitledBorder("Client data fields");
		panelFields.setBorder(titlePnlFields);

		//Painel para os botões:
		panelButtons = new JPanel(new FlowLayout());
		titlePnlButtons = BorderFactory.createTitledBorder("Actions button");
		panelButtons.setBorder(titlePnlButtons);

		//Painel para a tabela
		panelTable = new JPanel(new GridLayout(1, 1));
		titlePnlTable = BorderFactory.createTitledBorder("Customers data table");
		panelTable.setBorder(titlePnlTable);

		//Seta os labels
		labelName = new JLabel("Name");
		labelPhone = new JLabel("Phone");
		labelCity = new JLabel("City");
		txtName = new JTextField(50);

		//Cria um campo de texto com uma máscara para o telefone
		try {
			mskPhone = new MaskFormatter("+## (##) # ####-####");
			mskPhone.setPlaceholderCharacter('_');
		} catch (ParseException ex) {
			JOptionPane.showMessageDialog(null, "Incorrect phone format");
		}
		txtPhone = new JFormattedTextField(mskPhone);
		cmbCitiesModel = new DefaultComboBoxModel();
		cmbCities = new JComboBox(cmbCitiesModel);

		//Cities para o combo de seleção
		String firstCity = "São Paulo";
		String secondCity = "Rio de Janeiro";
		String thirdCity = "Campinas";

		//Acrescenta as cidades
		cmbCitiesModel.addElement(firstCity);
		cmbCitiesModel.addElement(secondCity);
		cmbCitiesModel.addElement(thirdCity);

		panelName = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelName.add(labelName);
		panelName.add(txtName);
		panelFields.add(panelName);

		panelPhone = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelPhone.add(labelPhone);
		panelPhone.add(txtPhone);
		panelFields.add(panelPhone);

		panelCity = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelCity.setBorder(new EmptyBorder(0, 0, 0, 0));
		panelCity.add(labelCity);
		panelCity.add(cmbCities);
		panelFields.add(panelCity);

		//Seta os botões
		btnAdd = new JButton("Add new client");
		btnAdd.addActionListener(this); //Adiciona o Listener ao botão
		btnClear = new JButton("Clear all clients");
		btnClear.addActionListener(this); //Adiciona o Listener ao botão
		panelButtons.add(btnAdd);
		panelButtons.add(btnClear);

		//Define a tabela
		String[] cols = {"Name", "Phone", "City"};
		tableClientModel = new DefaultTableModel(cols, 3);
		tableClient = new JTable(tableClientModel);
		tableClientModel.setNumRows(0);

		JScrollPane scrRolagem = new JScrollPane(tableClient,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panelTable.add(scrRolagem);

		add(panelFields, BorderLayout.NORTH);
		add(panelButtons, BorderLayout.CENTER);
		add(panelTable, BorderLayout.SOUTH);
	}

	//Método main
	public static void main(String[] args) {
		JavaGUIApplication ap = new JavaGUIApplication();
		ap.setVisible(true);
	}

	//mëtodo para setar algumas proprietdades da janela
	private void defineWindow() {
		setTitle("Java Store");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(1200, 680);
		setLocationRelativeTo(null);
	}

	//Sobrescrita obrigatória da interface ActionListener
	@Override
	public void actionPerformed(ActionEvent e) {
        /*
         A ideia é assim: É um listerner para todos os botões.
         O Listener deve identificar qual botão foi clicado e disparar
         uma ação específica para cada um
         */
		if (e.getSource() == btnAdd) { //Para o botão adicionar
			String[] linha = {txtName.getText(),
					txtPhone.getText(),
					cmbCitiesModel.getSelectedItem().toString()};
			tableClientModel.addRow(linha);
			JOptionPane.showMessageDialog(this, "Data added", "OK!", JOptionPane.PLAIN_MESSAGE);
		} else if (e.getSource() == btnClear) { //Para o botão limpar
			int op = JOptionPane.showConfirmDialog(this, "Do you really want to clear all data?", "Clear",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (op == 0) {
				txtName.setText(null);
				txtPhone.setText(null);
				cmbCitiesModel.setSelectedItem("São Paulo");
				txtName.requestFocus(); //Coloca o cursor no txtName
			}
		}
	}
}