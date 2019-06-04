package it.polito.tdp.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import it.polito.tdp.noleggio.model.Evento.TipoEvento;

public class Simulatore {
	//struttara base su cui lavoreranno i metodi del simulatore
	
	private PriorityQueue<Evento> queue = new PriorityQueue<>() ;
	
	//Stato del mondo
	
	  /*fotografia del sistema, nel momento in cui non accada nulla
	      - quante auto ho in totale
	      - quante auto sono disponibili 
	    */
	
	private int autoTotali;      // fisso
	private int autoDisponibili; //varia tra 0 e autoTotali
	
	
	//Parametri di simulazione
	
	  /*
	   * Serie di costanti da raccogliere in un unico punto
	   */
	
	 private LocalTime oraInizio = LocalTime.of(8, 0);
	 private LocalTime oraFine = LocalTime.of(20, 0);
	 
	 private Duration intervalloArrivoCliente = Duration.ofMinutes(10);
	 
	 private List<Duration> durataNoleggio;
	
	//Statistiche raccolte (esito simulazione)
	
	 private int numeroClientiTotali;
	 private int numeroClientiInsodisfatti;
	 
	 //variabili interne
	 
	 private Random rand = new Random();
	 
	 
	 public Simulatore() {
		 this.durataNoleggio = new ArrayList<Duration>();
		 
		 this.durataNoleggio.add(Duration.ofHours(1));
		 this.durataNoleggio.add(Duration.ofHours(2));
		 this.durataNoleggio.add(Duration.ofHours(3));
		 
	 }
	 
	 public void init(int autoTotali) {
		
		 this.autoTotali=autoTotali;
		 this.autoDisponibili=autoTotali;
		 this.numeroClientiTotali=0;
		 this.numeroClientiInsodisfatti=0;
		 
		 this.queue.clear();
		 
		 // carico gli eventi iniziali
		  //arriva un cliente ogni intervalloArrivoCliente a partire da oraInzio
		 
		 for(LocalTime  ora = oraInizio ; ora.isBefore(oraFine); ora = ora.plus(intervalloArrivoCliente)) {
			    queue.add(new Evento(ora, TipoEvento.CLIENTE_ARRIVA));
		 }
	 }
	
	 public void run() {
		 
		 while(!queue.isEmpty()) {
			 
			 Evento ev = queue.poll();
			 System.out.println(ev.toString());
			 
			 switch(ev.getTipo()) {
			 
			 case CLIENTE_ARRIVA: 
				 this.numeroClientiTotali++;
				 
				 if(this.autoDisponibili==0) 
					 this.numeroClientiInsodisfatti++;
				 else {
					 //noleggio un auto al cliente 
					 
					 this.autoDisponibili--;
					 
					 int i = rand.nextInt(this.durataNoleggio.size());
					 Duration noleggio = this.durataNoleggio.get(i);
					 LocalTime rientro = ev.getTempo().plus(noleggio);
				
					queue.add(new Evento(rientro, TipoEvento.AUTO_RESTITUITA)); 
				 }
				 break;
				 
			 case AUTO_RESTITUITA:
				 this.autoDisponibili++;
				 break;
			 
			 }
			 
			 
		 }
	 }

	public LocalTime getOraInizio() {
		return oraInizio;
	}

	public void setOraInizio(LocalTime oraInizio) {
		this.oraInizio = oraInizio;
	}

	public LocalTime getOraFine() {
		return oraFine;
	}

	public void setOraFine(LocalTime oraFine) {
		this.oraFine = oraFine;
	}

	public Duration getIntervalloArrivoCliente() {
		return intervalloArrivoCliente;
	}

	public void setIntervalloArrivoCliente(Duration intervalloArrivoCliente) {
		this.intervalloArrivoCliente = intervalloArrivoCliente;
	}

	public List<Duration> getDurataNoleggio() {
		return durataNoleggio;
	}

	public void setDurataNoleggio(List<Duration> durataNoleggio) {
		this.durataNoleggio = durataNoleggio;
	}

	public int getAutoTotali() {
		return autoTotali;
	}

	public int getAutoDisponibili() {
		return autoDisponibili;
	}

	public int getNumeroClientiTotali() {
		return numeroClientiTotali;
	}

	public int getNumeroClientiInsodisfatti() {
		return numeroClientiInsodisfatti;
	}

}
