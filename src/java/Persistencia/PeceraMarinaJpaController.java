
package Persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Habitante;
import Logica.PeceraMarina;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PeceraMarinaJpaController implements Serializable {

    public PeceraMarinaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public PeceraMarinaJpaController(){
        emf = Persistence.createEntityManagerFactory("FishAppPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PeceraMarina peceraMarina) {
        if (peceraMarina.getListaHabitantes() == null) {
            peceraMarina.setListaHabitantes(new ArrayList<Habitante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Habitante> attachedListaHabitantes = new ArrayList<Habitante>();
            for (Habitante listaHabitantesHabitanteToAttach : peceraMarina.getListaHabitantes()) {
                listaHabitantesHabitanteToAttach = em.getReference(listaHabitantesHabitanteToAttach.getClass(), listaHabitantesHabitanteToAttach.getIdHabitante());
                attachedListaHabitantes.add(listaHabitantesHabitanteToAttach);
            }
            peceraMarina.setListaHabitantes(attachedListaHabitantes);
            em.persist(peceraMarina);
            for (Habitante listaHabitantesHabitante : peceraMarina.getListaHabitantes()) {
                Logica.Pecera oldPeceraOfListaHabitantesHabitante = listaHabitantesHabitante.getPecera();
                listaHabitantesHabitante.setPecera(peceraMarina);
                listaHabitantesHabitante = em.merge(listaHabitantesHabitante);
                if (oldPeceraOfListaHabitantesHabitante != null) {
                    oldPeceraOfListaHabitantesHabitante.getListaHabitantes().remove(listaHabitantesHabitante);
                    oldPeceraOfListaHabitantesHabitante = em.merge(oldPeceraOfListaHabitantesHabitante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PeceraMarina peceraMarina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PeceraMarina persistentPeceraMarina = em.find(PeceraMarina.class, peceraMarina.getIdPecera());
            List<Habitante> listaHabitantesOld = persistentPeceraMarina.getListaHabitantes();
            List<Habitante> listaHabitantesNew = peceraMarina.getListaHabitantes();
            List<Habitante> attachedListaHabitantesNew = new ArrayList<Habitante>();
            for (Habitante listaHabitantesNewHabitanteToAttach : listaHabitantesNew) {
                listaHabitantesNewHabitanteToAttach = em.getReference(listaHabitantesNewHabitanteToAttach.getClass(), listaHabitantesNewHabitanteToAttach.getIdHabitante());
                attachedListaHabitantesNew.add(listaHabitantesNewHabitanteToAttach);
            }
            listaHabitantesNew = attachedListaHabitantesNew;
            peceraMarina.setListaHabitantes(listaHabitantesNew);
            peceraMarina = em.merge(peceraMarina);
            for (Habitante listaHabitantesOldHabitante : listaHabitantesOld) {
                if (!listaHabitantesNew.contains(listaHabitantesOldHabitante)) {
                    listaHabitantesOldHabitante.setPecera(null);
                    listaHabitantesOldHabitante = em.merge(listaHabitantesOldHabitante);
                }
            }
            for (Habitante listaHabitantesNewHabitante : listaHabitantesNew) {
                if (!listaHabitantesOld.contains(listaHabitantesNewHabitante)) {
                    PeceraMarina oldPeceraOfListaHabitantesNewHabitante = (PeceraMarina) listaHabitantesNewHabitante.getPecera();
                    listaHabitantesNewHabitante.setPecera(peceraMarina);
                    listaHabitantesNewHabitante = em.merge(listaHabitantesNewHabitante);
                    if (oldPeceraOfListaHabitantesNewHabitante != null && !oldPeceraOfListaHabitantesNewHabitante.equals(peceraMarina)) {
                        oldPeceraOfListaHabitantesNewHabitante.getListaHabitantes().remove(listaHabitantesNewHabitante);
                        oldPeceraOfListaHabitantesNewHabitante = em.merge(oldPeceraOfListaHabitantesNewHabitante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = peceraMarina.getIdPecera();
                if (findPeceraMarina(id) == null) {
                    throw new NonexistentEntityException("The peceraMarina with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PeceraMarina peceraMarina;
            try {
                peceraMarina = em.getReference(PeceraMarina.class, id);
                peceraMarina.getIdPecera();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The peceraMarina with id " + id + " no longer exists.", enfe);
            }
            List<Habitante> listaHabitantes = peceraMarina.getListaHabitantes();
            for (Habitante listaHabitantesHabitante : listaHabitantes) {
                listaHabitantesHabitante.setPecera(null);
                listaHabitantesHabitante = em.merge(listaHabitantesHabitante);
            }
            em.remove(peceraMarina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PeceraMarina> findPeceraMarinaEntities() {
        return findPeceraMarinaEntities(true, -1, -1);
    }

    public List<PeceraMarina> findPeceraMarinaEntities(int maxResults, int firstResult) {
        return findPeceraMarinaEntities(false, maxResults, firstResult);
    }

    private List<PeceraMarina> findPeceraMarinaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PeceraMarina.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PeceraMarina findPeceraMarina(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PeceraMarina.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeceraMarinaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PeceraMarina> rt = cq.from(PeceraMarina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
