public class Metropolises {
    private String m;
    private String c;
    private String p;

    public Metropolises(String metropolis, String continent, String population) {
        m = metropolis;
        c = continent;
        p = population;
    }

    public String getMetropolis()   {return m;  }

    public String getContinent()    {return c;  }

    public String getPopulation()   {return p;  }
}
