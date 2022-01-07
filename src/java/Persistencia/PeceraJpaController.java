
package Persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Habitante;
import Logica.Pecera;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PeceraJpaController implements Serializable {

    public PeceraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public PeceraJpaController(){
        emf = Persistence.createEntityManagerFactory("FishAppPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Pecera create(Pecera pecera) {
        if (pecera.getListaHabitantes() == null) {
            pecera.setListaHabitantes(new ArrayList<Habitante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Habitante> attachedListaHabitantes = new ArrayList<Habitante>();
            for (Habitante listaHabitantesHabitanteToAttach : pecera.getListaHabitantes()) {
                listaHabitantesHabitanteToAttach = em.getReference(listaHabitantesHabitanteToAttach.getClass(), listaHabitantesHabitanteToAttach.getIdHabitante());
                attachedListaHabitantes.add(listaHabitantesHabitanteToAttach);
            }
            pecera.setListaHabitantes(attachedListaHabitantes);
            em.persist(pecera);
            for (Habitante listaHabitantesHabitante : pecera.getListaHabitantes()) {
                Pecera oldPeceraOfListaHabitantesHabitante = listaHabitantesHabitante.getPecera();
                listaHabitantesHabitante.setPecera(pecera);
                listaHabitantesHabitante = em.merge(listaHabitantesHabitante);
                if (oldPeceraOfListaHabitantesHabitante != null) {
                    oldPeceraOfListaHabitantesHabitante.getListaHabitantes().remove(listaHabitantesHabitante);
                    oldPeceraOfListaHabitantesHabitante = em.merge(oldPeceraOfListaHabitantesHabitante);
                }
            }
            em.getTransaction().commit();
            return pecera;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pecera pecera) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pecera persistentPecera = em.find(Pecera.class, pecera.getIdPecera());
            List<Habitante> listaHabitantesOld = persistentPecera.getListaHabitantes();
            List<Habitante> listaHabitantesNew = pecera.getListaHabitantes();
            List<Habitante> attachedListaHabitantesNew = new ArrayList<Habitante>();
            for (Habitante listaHabitantesNewHabitanteToAttach : listaHabitantesNew) {
                listaHabitantesNewHabitanteToAttach = em.getReference(listaHabitantesNewHabitanteToAttach.getClass(), listaHabitantesNewHabitanteToAttach.getIdHabitante());
                attachedListaHabitantesNew.add(listaHabitantesNewHabitanteToAttach);
            }
            listaHabitantesNew = attachedListaHabitantesNew;
            pecera.setListaHabitantes(listaHabitantesNew);
            pecera = em.merge(pecera);
            for (Habitante listaHabitantesOldHabitante : listaHabitantesOld) {
                if (!listaHabitantesNew.contains(listaHabitantesOldHabitante)) {
                    listaHabitantesOldHabitante.setPecera(null);
                    listaHabitantesOldHabitante = em.merge(listaHabitantesOldHabitante);
                }
            }
            for (Habitante listaHabitantesNewHabitante : listaHabitantesNew) {
                if (!listaHabitantesOld.contains(listaHabitantesNewHabitante)) {
                    Pecera oldPeceraOfListaHabitantesNewHabitante = listaHabitantesNewHabitante.getPecera();
                    listaHabitantesNewHabitante.setPecera(pecera);
                    listaHabitantesNewHabitante = em.merge(listaHabitantesNewHabitante);
                    if (oldPeceraOfListaHabitantesNewHabitante != null && !oldPeceraOfListaHabitantesNewHabitante.equals(pecera)) {
                        oldPeceraOfListaHabitantesNewHabitante.getListaHabitantes().remove(listaHabitantesNewHabitante);
                        oldPeceraOfListaHabitantesNewHabitante = em.merge(oldPeceraOfListaHabitantesNewHabitante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = pecera.getIdPecera();
                if (findPecera(id) == null) {
                    throw new NonexistentEntityException("The pecera with id " + id + " no longer exists.");
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
            Pecera pecera;
            try {
                pecera = em.getReference(Pecera.class, id);
                pecera.getIdPecera();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pecera with id " + id + " no longer exists.", enfe);
            }
            List<Habitante> listaHabitantes = pecera.getListaHabitantes();
            for (Habitante listaHabitantesHabitante : listaHabitantes) {
                listaHabitantesHabitante.setPecera(null);
                listaHabitantesHabitante = em.merge(listaHabitantesHabitante);
            }
            em.remove(pecera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pecera> findPeceraEntities() {
        return findPeceraEntities(true, -1, -1);
    }

    public List<Pecera> findPeceraEntities(int maxResults, int firstResult) {
        return findPeceraEntities(false, maxResults, firstResult);
    }

    private List<Pecera> findPeceraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pecera.class));
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

    public Pecera findPecera(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pecera.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeceraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pecera> rt = cq.from(Pecera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
