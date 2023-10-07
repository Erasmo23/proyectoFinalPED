package sv.edu.udbvirtual.commons.datatables.criteria;

import java.util.Optional;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import sv.edu.udbvirtual.commons.datatables.mapping.Column;

/** Esta clase guarda los joins creados al hacer un join a una entidad. Estos
 *  joins son guardados temporalmente para evitar crear mas de un join por cada 
 *  entidad relacionada en un query de CriteriaQuery
 * 
 * @author VOlivares
 */
public class EntradaCacheJoins {
    private From<Object, Object> from;
    private CriteriaQuery<?> query;
    private RecursivePredicateCreator<Object> recursivePredicateCreator;

    public EntradaCacheJoins(From<?, ?> from, CriteriaQuery<?> query, RecursivePredicateCreator<?> recursivePredicateCreator) {
        this.from = (From<Object, Object>) from;
        this.query = query;
        this.recursivePredicateCreator = (RecursivePredicateCreator<Object>) recursivePredicateCreator;
    }

    public From<Object, Object> getFrom() {
        return from;
    }

    public RecursivePredicateCreator<Object> getRecursivePredicateCreator() {
        return recursivePredicateCreator;
    }
    
    public Optional<Predicate> crearPredicate(CriteriaBuilder cb, Column filtro) throws NoSuchFieldException {
        return recursivePredicateCreator.crearPredicateDeColumna(from, query, cb, filtro);
    }
}
