package org.realityforge.antix;

import java.util.Vector;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Property;
import org.apache.tools.ant.types.RegularExpression;
import org.apache.tools.ant.util.regexp.Regexp;

public class SelectRegexTask
   extends Task
{
   private RegularExpression pattern;
   private String select;
   private String property;
   private String value;

   public void setProperty( final String property )
   {
      this.property = property;
   }

   public void setSelect( final String select )
   {
      this.select = select;
   }

   public void setValue( final String value )
   {
      this.value = value;
   }

   public void setPattern( final String pattern )
   {
      this.pattern = new RegularExpression();
      this.pattern.setPattern( pattern );
   }

   public void execute()
   {
      validate();

      final String output = performMatching( value );
      if ( output != null )
      {
         final Property p = (Property) getProject().createTask( "property" );
         p.setName( property );
         p.setValue( output );
         p.execute();
      }
   }

   private String performMatching( final String input )
   {
      final Regexp regexp = this.pattern.getRegexp( getProject() );
      final Vector groups = regexp.getGroups( input, 0 );
      if ( groups != null && !groups.isEmpty() )
      {
         String output = select;
         final int count = groups.size();
         for ( int i = 0; i < count; i++ )
         {
            final String group = (String) groups.get( i );
            output = output.replace( "\\" + i, group );
         }
         return output;
      }
      return null;
   }

   private void validate()
   {
      if ( null == property )
      {
         throw new BuildException( "Property not set." );
      }
      if ( null == pattern )
      {
         throw new BuildException( "No regular expression specified." );
      }
      if ( null == select )
      {
         throw new BuildException( "Select not set." );
      }
      if ( null == value )
      {
         throw new BuildException( "Value not set." );
      }
   }
}