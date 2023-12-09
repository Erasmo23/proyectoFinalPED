package sv.edu.udbvirtual.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;

import sv.edu.udbvirtual.commons.datatables.repository.DataTablesRepository;
import sv.edu.udbvirtual.domain.CcEtiqueta;

public interface CcEtiquetaRepository extends DataTablesRepository<CcEtiqueta, Integer> {
	
	@Query(value = "select t from CcEtiqueta t where t.descripcion like '%' || :query || '%' order by t.descripcion")
	Slice<CcEtiqueta> findByDescripcionLike(String query, Pageable page);

	@Query(value = "select t from CcEtiqueta t where t.estado=true and t.descripcion like '%' || :query || '%' order by t.descripcion")
	Slice<CcEtiqueta> findByDescripcionLikeActivos(String query, Pageable page);

}