package it.polito.tdp.noleggio.model;

import java.time.LocalTime;

public class Evento implements Comparable<Evento> {
	
	
	//classe che contiene una serie di costanti (dentro la classe evento)
	
	public enum TipoEvento {
		CLIENTE_ARRIVA,
		AUTO_RESTITUITA
	}
	
	public Evento(LocalTime tempo, TipoEvento tipo) {
		
		this.tempo = tempo;
		this.tipo = tipo;
	}

	private LocalTime tempo ;
	private TipoEvento tipo ;
	
	public LocalTime getTempo() {
		return tempo;
	}

	public TipoEvento getTipo() {
		return tipo;
	}

	//interessa solo la marcatura temporale
	@Override
	public int compareTo(Evento other) {
		return this.tempo.compareTo(other.tempo);
	}

	@Override
	public String toString() {
		return "Evento [tempo=" + tempo + ", tipo=" + tipo + "]";
	}
	
	
}
