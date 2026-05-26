package src.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import src.dao.MovimentacaoPatrimonioDao;
import src.enums.StatusPatrimonioEnum;
import src.enums.TipoMovimentacaoEnum;
import src.model.MovimentacaoPatrimonio;
import src.repository.DbSession;

public class MovimentacaoPatrimonioDaoImpl implements MovimentacaoPatrimonioDao {

    private static MovimentacaoPatrimonio mapResultSetToObject(ResultSet rs) throws Exception {

        Long idResponsavelAnterior = rs.getLong("id_responsavel_anterior");
        if (rs.wasNull()) {
            idResponsavelAnterior = null;
        }

        Long idResponsavelAtual = rs.getLong("id_responsavel_atual");
        if (rs.wasNull()) {
            idResponsavelAtual = null;
        }

        return new MovimentacaoPatrimonio(
                null,
                rs.getLong("id_patrimonio"),
                TipoMovimentacaoEnum.fromCodigo(rs.getInt("tipo_movimentacao")),
                StatusPatrimonioEnum.fromCodigo(rs.getInt("status_anterior")),
                StatusPatrimonioEnum.fromCodigo(rs.getInt("status_atual")),
                idResponsavelAnterior,
                idResponsavelAtual,
                rs.getTimestamp("data_movimentacao").toLocalDateTime(),
                rs.getString("observacao"));
    }

    private static void mapObjectToPreparedStatement(
            PreparedStatement ps,
            MovimentacaoPatrimonio movimentacao) throws Exception {

        ps.setLong(
                1,
                movimentacao.getIdPatrimonio());

        ps.setInt(
                2,
                movimentacao.getTipoMovimentacao().getCodigo());

        if (movimentacao.getStatusAnterior() != null) {

            ps.setInt(
                    3,
                    movimentacao.getStatusAnterior().getCodigo());

        } else {

            ps.setNull(
                    3,
                    java.sql.Types.INTEGER);
        }

        if (movimentacao.getStatusAtual() != null) {

            ps.setInt(
                    4,
                    movimentacao.getStatusAtual().getCodigo());

        } else {

            ps.setNull(
                    4,
                    java.sql.Types.INTEGER);
        }

        if (movimentacao.getIdResponsavelAnterior() != null) {

            ps.setLong(
                    5,
                    movimentacao.getIdResponsavelAnterior());

        } else {

            ps.setNull(
                    5,
                    java.sql.Types.BIGINT);
        }

        if (movimentacao.getIdResponsavelAtual() != null) {

            ps.setLong(
                    6,
                    movimentacao.getIdResponsavelAtual());

        } else {

            ps.setNull(
                    6,
                    java.sql.Types.BIGINT);
        }

        ps.setTimestamp(
                7,
                Timestamp.valueOf(
                        movimentacao.getDataMovimentacao()));

        ps.setString(
                8,
                movimentacao.getObservacao());
    }

    @Override
    public List<MovimentacaoPatrimonio> findAll() {
        try (Connection connection = DbSession.startDbSession()) {

            final String SQL = "SELECT * FROM movimentacao_patrimonio";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            List<MovimentacaoPatrimonio> list = new ArrayList<>();
            while (rs.next())
                list.add(mapResultSetToObject(rs));

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public MovimentacaoPatrimonio findById(Long id) {
        try (Connection connection = DbSession.startDbSession()) {

            final String SQL = "SELECT * FROM movimentacao_patrimonio WHERE id = ?";
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
    public void save(MovimentacaoPatrimonio movimentacao) {
        try (Connection connection = DbSession.startDbSession()) {

            final String SQL = """
                    INSERT INTO movimentacao_patrimonio (
                        id_patrimonio,
                        tipo_movimentacao,
                        status_anterior,
                        status_atual,
                        id_responsavel_anterior,
                        id_responsavel_atual,
                        data_movimentacao,
                        observacao
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                    """;
            PreparedStatement ps = connection.prepareStatement(SQL);
            mapObjectToPreparedStatement(ps, movimentacao);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long countByResponsavelAnteriorOrAtual(Long idResponsavel) {
        try (Connection connection = DbSession.startDbSession()) {

            final String SQL = "SELECT COUNT(1) FROM movimentacao_patrimonio WHERE id_responsavel_anterior = ? OR id_responsavel_atual = ?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setLong(1, idResponsavel);
            ps.setLong(2, idResponsavel);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getLong(1) : 0L;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override
    public void deleteByResponsavelAnteriorOrAtual(Long idResponsavel) {
        try (Connection connection = DbSession.startDbSession()) {

            final String SQL = "DELETE FROM movimentacao_patrimonio WHERE id_responsavel_anterior = ? OR id_responsavel_atual = ?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setLong(1, idResponsavel);
            ps.setLong(2, idResponsavel);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long countByIdPatrimonio(Long idPatrimonio) {
        try (Connection connection = DbSession.startDbSession()) {

            final String SQL = "SELECT COUNT(1) FROM movimentacao_patrimonio WHERE id_patrimonio = ?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setLong(1, idPatrimonio);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getLong(1) : 0L;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override
    public void deleteByIdPatrimonio(Long idPatrimonio) {
        try (Connection connection = DbSession.startDbSession()) {

            final String SQL = "DELETE FROM movimentacao_patrimonio WHERE id_patrimonio = ?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setLong(1, idPatrimonio);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}