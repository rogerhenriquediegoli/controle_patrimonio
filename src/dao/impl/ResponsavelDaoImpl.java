package src.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import src.dao.ResponsavelDao;
import src.enums.SexoEnum;
import src.enums.StatusResponsavelEnum;
import src.model.Responsavel;
import src.repository.DbSession;

public class ResponsavelDaoImpl implements ResponsavelDao {

    private static Responsavel mapResultSetToObject(ResultSet rs) throws SQLException {

        return new Responsavel(
                rs.getLong("id"),
                rs.getString("nome_completo"),
                rs.getString("cpf"),
                rs.getString("email"),
                rs.getString("telefone"),
                rs.getString("setor"),
                rs.getString("cargo"),
                SexoEnum.valueOf(rs.getString("sexo")),
                rs.getTimestamp("data_cadastro").toLocalDateTime(),
                StatusResponsavelEnum.valueOf(rs.getString("status")));
    }

    private static void mapObjectToPreparedStatement(PreparedStatement ps, Responsavel responsavel, Boolean isUpdate)
            throws SQLException {
        ps.setString(1, responsavel.getNomeCompleto());
        ps.setString(2, responsavel.getCpf());
        ps.setString(3, responsavel.getEmail());
        ps.setString(4, responsavel.getTelefone());
        ps.setString(5, responsavel.getSetor());
        ps.setString(6, responsavel.getCargo());
        ps.setString(7, responsavel.getSexo().getCodigo());
        ps.setInt(8, responsavel.getStatus().getCodigo());
        if (Boolean.TRUE.equals(isUpdate))
            ps.setLong(9, responsavel.getId());
    }

    @Override
    public List<Responsavel> findAll() {
        try(Connection dbConnection = DbSession.startDbSession()) {

            final String SQL = "SELECT * FROM responsavel";
            PreparedStatement ps = dbConnection.prepareStatement(SQL);

            List<Responsavel> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();

            while (rs.next())
                list.add(mapResultSetToObject(rs));

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Responsavel findById(Long id) {
        try(Connection dbConnection = DbSession.startDbSession()) {

            final String SQL = "SELECT * FROM responsavel WHERE id = ?";
            PreparedStatement ps = dbConnection.prepareStatement(SQL);
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            Responsavel responsavel = rs.next() ? mapResultSetToObject(rs) : null;

            return responsavel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(Responsavel responsavel) {
        try(Connection connection = DbSession.startDbSession()) {

            final String SQL = """
                        INSERT INTO responsavel (
                            nome_completo,
                            cpf,
                            email,
                            telefone,
                            setor,
                            cargo,
                            sexo,
                            status
                        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                    """;
            PreparedStatement ps = connection.prepareStatement(SQL);
            mapObjectToPreparedStatement(ps, responsavel, false);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Responsavel responsavel) {
        try(Connection connection = DbSession.startDbSession()) {

            final String SQL = """
                        UPDATE responsavel SET
                            nome_completo = ?,
                            cpf = ?,
                            email = ?,
                            telefone = ?,
                            setor = ?,
                            cargo = ?,
                            sexo = ?,
                            status = ?
                        WHERE id = ?
                    """;
            PreparedStatement ps = connection.prepareStatement(SQL);
            mapObjectToPreparedStatement(ps, responsavel, true);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        try(Connection dbConnection = DbSession.startDbSession()) {

            final String SQL = "DELETE FROM responsavel WHERE id = ?";
            PreparedStatement ps = dbConnection.prepareStatement(SQL);
            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}