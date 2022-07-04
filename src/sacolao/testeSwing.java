package sacolao;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class testeSwing {
	public static void main(String[] args) {
		// Define a janela
		JFrame janela = new JFrame("Cadastro de produto"); // Janela Normal
		janela.setResizable(false); // A janela não poderá ter o tamanho ajustado
		janela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		janela.setSize(400, 300); // Define tamanho da janela
		// Define o layout da janela
		Container caixa = janela.getContentPane();
		caixa.setLayout(null);
		// Define os labels dos campos
		JLabel labelId = new JLabel("Id: ");
		JLabel labelNome = new JLabel("Nome do Produto: ");
		JLabel labelTipo = new JLabel("Tipo do Produto: ");
		JLabel labelPreco = new JLabel("Preco: ");
		// Posiciona os labels na janela
		labelId.setBounds(50, 40, 100, 20); // coluna, linha, largura, tamanho
		labelNome.setBounds(50, 80, 150, 20); // coluna, linha, largura, tamanho
		labelTipo.setBounds(50, 120, 100, 20); // coluna, linha, largura, tamanho
		labelPreco.setBounds(50, 160, 100, 20); // coluna, linha, largura, tamanho
		// Define os input box
		JTextField jTextId = new JTextField();
		JTextField jTextNome = new JTextField();
		JTextField jTextTipo = new JTextField();
		JTextField jTextPreco = new JTextField();
		// Define se os campos estão habilitados ou não no início
		jTextId.setEnabled(true);
		jTextNome.setEnabled(false);
		jTextTipo.setEnabled(false);
		jTextPreco.setEnabled(false);
		// Posiciona os input box
		jTextId.setBounds(180, 40, 50, 20);
		jTextNome.setBounds(180, 80, 50, 20);
		jTextTipo.setBounds(180, 120, 150, 20);
		jTextPreco.setBounds(180, 160, 150, 20);
		// Adiciona os rótulos e os input box na janela
		janela.add(labelId);
		janela.add(labelNome);
		janela.add(labelTipo);
		janela.add(labelPreco);
		janela.add(jTextId);
		janela.add(jTextNome);
		janela.add(jTextTipo);
		janela.add(jTextPreco);
		// Define botões e a localização deles na janela
		JButton botaoConsultar = new JButton("Consultar");
		botaoConsultar.setBounds(230, 40, 100, 20);
		janela.add(botaoConsultar);
		JButton botaoGravar = new JButton("Gravar");
		botaoGravar.setBounds(50, 200, 100, 20);
		botaoGravar.setEnabled(false);
		janela.add(botaoGravar);
		JButton botaoDeletar = new JButton("Deletar");
		botaoDeletar.setBounds(150, 200, 100, 20);
		botaoDeletar.setEnabled(false);
		janela.add(botaoDeletar);
		JButton botaoLimpar = new JButton("Limpar");
		botaoLimpar.setBounds(250, 200, 100, 20);
		botaoLimpar.setEnabled(false);
		janela.add(botaoLimpar);
		// Define objeto conta para pesquisar no banco de dados
		Produto produto = new Produto();
		// Define ações dos botões
		botaoConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(jTextId.getText());
					String nome, tipo, preco;
					if (!produto.consultarProduto(id)) {
						nome = "";
					}
					else {
						nome = produto.getNome();
						tipo = produto.getTipo();
						preco = Double.toString(produto.getPreco());
						jTextNome.setText(nome);
						jTextTipo.setText(tipo);
						jTextPreco.setText(preco);
						botaoDeletar.setEnabled(true);
					}
					jTextId.setEnabled(false);
					jTextNome.setEnabled(true);
					jTextNome.requestFocus();
					botaoConsultar.setEnabled(false);
					botaoGravar.setEnabled(true);
					botaoLimpar.setEnabled(true);
					jTextTipo.setEnabled(true);
					jTextPreco.setEnabled(true);
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(janela, "Preencha o campo ID");
				}
			}
		});

		botaoDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(jTextId.getText());
				produto.deletarProduto(id);
				
				}
		});
		botaoLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextId.setText(""); // Limpar campo
				jTextNome.setText(""); // Limpar campo
				jTextTipo.setText(""); // Limpar campo
				jTextPreco.setText(""); // Limpar campo
				jTextId.setEnabled(true);
				jTextNome.setEnabled(false);
				jTextTipo.setEnabled(false);
				jTextPreco.setEnabled(false);
				botaoConsultar.setEnabled(true);
				botaoGravar.setEnabled(false);
				botaoDeletar.setEnabled(false);
				jTextId.requestFocus(); // Colocar o foco em um campo
			}
		});
		botaoGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(jTextId.getText());
				String nome = jTextNome.getText().trim(); // Retira os espaços em branco
				String tipo = jTextTipo.getText().trim(); // Retira os espaços em branco
				String preco = jTextPreco.getText().trim(); // Retira os espaços em branco
				if (nome.length()==0) {
					JOptionPane.showMessageDialog(janela, "Preencha o campo titular");
					jTextNome.requestFocus();
				}
				else if(tipo.length()==0){
					JOptionPane.showMessageDialog(janela, "Preencha o campo tipo");
					jTextTipo.requestFocus();
				}
				else if(preco.length()==0){
					JOptionPane.showMessageDialog(janela, "Preencha o campo preco");
					jTextPreco.requestFocus();
				}
				else {
					if (!produto.consultarProduto(id)) {
						if (!produto.cadastrarProduto(id, tipo, nome, Double.parseDouble(preco)))
							JOptionPane.showMessageDialog(janela, "Erro na inclusão do produto!");
						else
							JOptionPane.showMessageDialog(janela, "Inclusão realizada!");
					} else {
						if (!produto.atualizarProduto(id, tipo, nome, Double.parseDouble(preco)))
							JOptionPane.showMessageDialog(janela, "Erro na atualização do produto!");
						else
							JOptionPane.showMessageDialog(janela, "Alteração realizada!");
					}
				}
				}
			}
		);
		// Apresenta a janela
		janela.setVisible(true); // Exibe a janela
	}
}