package org.realityforge.antix;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.MacroDef;
import org.apache.tools.ant.taskdefs.MacroInstance;

/**
 * A looping construct for ant. The task will accept a list of space separated
 * values. The task will then iterate over inner macro with name passed in as a parameter.
 */
public class ForEachTask
   extends Task
{
   private String list;
   private String property;
   private MacroDef macroDef;

   public void setProperty( final String property )
   {
      this.property = property;
   }

   public void setList( final String list )
   {
      this.list = list;
   }

   public MacroDef.NestedSequential createSequential()
   {
      macroDef = new MacroDef();
      macroDef.setProject( getProject() );
      return macroDef.createSequential();
   }

   public void execute()
   {
      validate();

      final MacroDef.Attribute attribute = new MacroDef.Attribute();
      attribute.setName( property );
      macroDef.addConfiguredAttribute( attribute );
      final String[] values = list.split( " " );
      if ( 0 != values.length )
      {
         for ( final String value : values )
         {
            final MacroInstance i = new MacroInstance();
            i.setProject( getProject() );
            i.setOwningTarget( getOwningTarget() );
            i.setMacroDef( macroDef );
            i.setDynamicAttribute( property, value );
            i.execute();
         }
      }
   }

   private void validate()
   {
      if ( null == list )
      {
         throw new BuildException( "List not set." );
      }
      if ( null == macroDef )
      {
         throw new BuildException( "Must specify a sequential task to iterate over." );
      }
      if ( null == property )
      {
         throw new BuildException( "Property not set." );
      }
   }
}
