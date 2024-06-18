import DAO.UsuarioDAO;
import entity.Usuario;
import java.util.List;
import java.util.Scanner;
import Conexao.Conexao;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        String menu = "1 - Cadastrar / 2 - Atualizar / 3 - Apagar / 4 - Listar / 0 - Sair";
        int op;
        do {
            System.out.println(menu);
            op = scanner.nextInt();
            scanner.nextLine();

            switch (op) {
                case 1:
                    System.out.println("Nome:");
                    String nome = scanner.nextLine();

                    System.out.println("Login:");
                    String login = scanner.nextLine();

                    System.out.println("Senha:");
                    String senha = scanner.nextLine();

                    System.out.println("E-mail:");
                    String email = scanner.nextLine();

                    Usuario novoUsuario = new Usuario();
                    novoUsuario.setNome(nome);
                    novoUsuario.setLogin(login);
                    novoUsuario.setSenha(senha);
                    novoUsuario.setEmail(email);

                    if (usuarioDAO.inserir(novoUsuario)) {
                        System.out.println("Usuário cadastrado com sucesso!");
                    } else {
                        System.out.println("Erro ao cadastrar usuário.");
                    }
                    break;

                case 2:
                    System.out.println("Código do usuário a ser atualizado:");
                    int codigoAtualizar = scanner.nextInt();
                    scanner.nextLine();

                    if (!usuarioDAO.usuarioExiste(codigoAtualizar)) {
                        System.out.println("Usuário com o código " + codigoAtualizar + " não encontrado.");
                        break;
                    }

                    System.out.println("Novo nome:");
                    String novoNome = scanner.nextLine();

                    System.out.println("Novo login:");
                    String novoLogin = scanner.nextLine();

                    System.out.println("Nova senha:");
                    String novaSenha = scanner.nextLine();

                    System.out.println("Novo e-mail:");
                    String novoEmail = scanner.nextLine();

                    Usuario usuarioAtualizar = new Usuario();
                    usuarioAtualizar.setIdusuario(codigoAtualizar);
                    usuarioAtualizar.setNome(novoNome);
                    usuarioAtualizar.setLogin(novoLogin);
                    usuarioAtualizar.setSenha(novaSenha);
                    usuarioAtualizar.setEmail(novoEmail);

                    if (usuarioDAO.atualizar(usuarioAtualizar)) {
                        System.out.println("Usuário atualizado com sucesso!");
                    } else {
                        System.out.println("Erro ao atualizar usuário.");
                    }
                    break;

                case 3:
                    System.out.println("Código do usuário a ser removido:");
                    int codigoRemover = scanner.nextInt();
                    scanner.nextLine();

                    if (usuarioDAO.apagar(codigoRemover)) {
                        System.out.println("Usuário removido com sucesso!");
                    } else {
                        System.out.println("Erro ao remover usuário.");
                    }
                    break;

                case 4:
                    System.out.println("Listagem de usuários:");
                    List<Usuario> usuarios = usuarioDAO.listar();
                    for (Usuario usuario : usuarios) {
                        System.out.println(usuario);
                    }
                    break;

                case 0:
                    System.out.println("Saindo...");
                    Conexao.closeConexao();
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (op != 0);

        scanner.close();
    }
}
