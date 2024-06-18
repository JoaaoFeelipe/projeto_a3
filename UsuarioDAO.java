package DAO;

import Conexao.Conexao;
import entity.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public boolean inserir(Usuario usuario) {
        String sql = "INSERT INTO cadastro (nome, login, senha, email) VALUES (?, ?, ?, ?)";
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getLogin());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getEmail());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(Usuario usuario) {
        String sql = "UPDATE cadastro SET nome = ?, login = ?, senha = ?, email = ? WHERE id = ?";
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getLogin());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getEmail());
            ps.setInt(5, usuario.getIdusuario());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean apagar(int idusuario) {
        String checkSql = "SELECT COUNT(*) FROM cadastro WHERE id = ?";
        String deleteSql = "DELETE FROM cadastro WHERE id = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement checkPs = conexao.prepareStatement(checkSql)) {
             
            checkPs.setInt(1, idusuario);
            ResultSet rs = checkPs.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                try (PreparedStatement deletePs = conexao.prepareStatement(deleteSql)) {
                    deletePs.setInt(1, idusuario);
                    deletePs.executeUpdate();
                    return true;
                }
            } else {
                System.out.println("Nenhum usuário encontrado com o código informado.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean usuarioExiste(int idusuario) {
        String sql = "SELECT COUNT(*) FROM cadastro WHERE id = ?";
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idusuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM cadastro";
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdusuario(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setEmail(rs.getString("email"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}
