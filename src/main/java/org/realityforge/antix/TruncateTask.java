package org.realityforge.antix;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * If the file is greater than the specified number of bytes then truncate.
 */
public class TruncateTask extends Task
{
  private File file;
  private int size;

  public void setFile( final File file )
  {
    this.file = file;
  }

  public void setSize( final int size )
  {
    this.size = size;
  }

  public void execute() throws BuildException
  {
    if( null == file ) throw new BuildException( "file not set." );
    if( 0 >= size ) throw new BuildException( "size not set to a value greater than 0." );
    if( file.length() < size )
    {
      return;
    }
    try
    {
      final RandomAccessFile random = new RandomAccessFile( file, "rw" );
      random.setLength( size );
      random.close();
    }
    catch( IOException e )
    {
      throw new BuildException( "Error truncating file " + file, e );
    }
  }
}