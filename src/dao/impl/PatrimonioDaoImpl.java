package src.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import src.dao.PatrimonioDao;
import src.enums.StatusPatrimonioEnum;
import src.model.Patrimonio;
import src.repository.DbSession;

public class PatrimonioDaoImpl implements PatrimonioDao {

    private static Patrimonio mapResultSetToObject(ResultSet rs) throws Exception {
        Long idResponsavel = rs.getLong("id_responsavel");
        if (rs.wasNull()) {
            idResponsavel = null;
        }

        return new Patrimonio(
                rs.getLong("id"),
                rs.getString("descricao"),
                rs.getString("numero_patrimonio"),
                StatusPatrimonioEnum.fromCodigo(rs.getInt("status")),
                idResponsavel);
    }

    private static void mapObjectToPreparedStatement(PreparedStatement ps, Patrimonio patrimonio, Boolean isUpdate)
            throws Exception {
        ps.setString(1, patrimonio.getDescricao());
        ps.setString(2, patrimonio.getNumeroPatrimonio());
        ps.setInt(3, patrimonio.getStatus().getCodigo());

        if (patrimonio.getIdResponsavel() != null) {
            ps.setLong(4, patrimonio.getIdResponsavel());
        } else {
            ps.setNull(4, java.sql.Types.BIGINT);
        }

        if (Boolean.TRUE.equals(isUpdate))
            ps.setLong(5, patrimonio.getId());
    }

    @Override
    public List<Patrimonio> findAll() {
        try {
            Connection connection = DbSession.startDbSession();

            final String SQL = "SELECT * FROM patrimonio";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            List<Patrimonio> list = new ArrayList<>();
            while (rs.next())
                list.add(mapResultSetToObject(rs));

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Patrimonio findById(Long id) {
        try (Connection connection = DbSession.startDbSession()) {

            final String SQL = "SELECT * FROM patrimonio WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            return rs.next() ? mapResultSetToObject(rs) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(Patrimonio patrimonio) {
        try (Connection connection = DbSession.startDbSession()) {

            final String SQL = """
                        INSERT INTO patrimonio (
                            descricao,
                            numero_patrimonio,
                            status,
                            id_responsavel
                        ) VALUES (?, ?, ?, ?)
                    """;
            PreparedStatement ps = connection.prepareStatement(SQL);
            mapObjectToPreparedStatement(ps, patrimonio, false);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Patrimonio patrimonio) {
        try (Connection connection = DbSession.startDbSession()) {

            final String SQL = """
                        UPDATE patrimonio SET
                            descricao = ?,
                            numero_patrimonio = ?,
                            status = ?,
                            id_responsavel = ?
                        WHERE id = ?
                    """;
            PreparedStatement ps = connection.prepareStatement(SQL);
            mapObjectToPreparedStatement(ps, patrimonio, true);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = DbSession.startDbSession()) {

            final String SQL = "DELETE FROM patrimonio WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long countByIdResponsavel(Long idResponsavel) {
        try (Connection connection = DbSession.startDbSession()) {

            final String SQL = "SELECT COUNT(1) FROM patrimonio WHERE id_responsavel = ?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setLong(1, idResponsavel);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getLong(1) : 0L;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
}