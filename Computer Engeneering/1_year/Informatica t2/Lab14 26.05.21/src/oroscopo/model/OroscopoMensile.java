package oroscopo.model;

public class OroscopoMensile implements Oroscopo, Comparable<Oroscopo>{
	private Previsione amore;
	private Previsione lavoro;
	private Previsione salute;
	private SegnoZodiacale segnoZodiacale;
	
	private SegnoZodiacale getSegnoZodiacaleFrom(String nomeSegnoZodiacale) {
		return SegnoZodiacale.valueOf(nomeSegnoZodiacale);
	}
	
	public OroscopoMensile(String nomeSegnoZodiacale, Previsione amore, Previsione lavoro, Previsione salute) throws IllegalArgumentException{
		if(nomeSegnoZodiacale==null || amore==null || lavoro==null || salute==null) { throw new IllegalArgumentException("Argomenti nulli");}
		else if(!amore.validaPerSegno(this.getSegnoZodiacaleFrom(nomeSegnoZodiacale)) || 
				!lavoro.validaPerSegno(this.getSegnoZodiacaleFrom(nomeSegnoZodiacale)) ||
				!salute.validaPerSegno(this.getSegnoZodiacaleFrom(nomeSegnoZodiacale))) {throw new IllegalArgumentException("Previsioni non valide");}
		else if(this.getSegnoZodiacaleFrom(nomeSegnoZodiacale)==null) {throw new IllegalArgumentException("Stringa non valida");}
		else {	
			this.segnoZodiacale=this.getSegnoZodiacaleFrom(nomeSegnoZodiacale);
			this.amore = amore;
			this.lavoro = lavoro;
			this.salute = salute;
		}
	}

	public OroscopoMensile(SegnoZodiacale segnoZodiacale, Previsione amore, Previsione lavoro, Previsione salute) {
		if(segnoZodiacale==null || amore==null || lavoro==null || salute==null) { throw new IllegalArgumentException("Argomenti nulli");}
		else if(!amore.validaPerSegno(segnoZodiacale) || 
				!lavoro.validaPerSegno(segnoZodiacale) ||
				!salute.validaPerSegno(segnoZodiacale)) {throw new IllegalArgumentException("Previsioni non valide");}
		else {
			this.segnoZodiacale = segnoZodiacale;
			this.amore = amore;
			this.lavoro = lavoro;
			this.salute = salute;
		}
	}

	@Override
	public int compareTo(Oroscopo o) {
		return this.segnoZodiacale.compareTo(o.getSegnoZodiacale());
	}

	@Override
	public SegnoZodiacale getSegnoZodiacale() {
		return this.segnoZodiacale;
	}

	@Override
	public Previsione getPrevisioneAmore() {
		return this.amore;
	}

	@Override
	public Previsione getPrevisioneSalute() {
		return this.salute;
	}

	@Override
	public Previsione getPrevisioneLavoro() {
		return this.lavoro;
	}

	@Override
	public int getFortuna() {
		long fortuna = Math.round((this.amore.getValore()+this.lavoro.getValore()+this.salute.getValore())/3.0);
		return (int) fortuna;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amore == null) ? 0 : amore.hashCode());
		result = prime * result + ((lavoro == null) ? 0 : lavoro.hashCode());
		result = prime * result + ((salute == null) ? 0 : salute.hashCode());
		result = prime * result + ((segnoZodiacale == null) ? 0 : segnoZodiacale.hashCode());
		return result;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OroscopoMensile other = (OroscopoMensile) obj;
		if (amore == null) {
			if (other.amore != null)
				return false;
		} else if (!amore.equals(other.amore))
			return false;
		if (lavoro == null) {
			if (other.lavoro != null)
				return false;
		} else if (!lavoro.equals(other.lavoro))
			return false;
		if (salute == null) {
			if (other.salute != null)
				return false;
		} else if (!salute.equals(other.salute))
			return false;
		if (segnoZodiacale != other.segnoZodiacale)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Amore= "+this.amore.getPrevisione()+"\nLavoro= "+this.lavoro.getPrevisione()+"\nSalute= "+this.salute.getPrevisione();
	}
}
