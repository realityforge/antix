repositories.remote << "http://www.ibiblio.org/maven2/"

desc 'A set of ant tasks that are used across a range of projects'
define 'antix' do
  project.version = '1.0.0'
  project.group = 'org.realityforge'

  compile.options.source = '1.6'
  compile.options.target = '1.6'
  compile.options.lint = 'all'

  compile.with 'org.apache.ant:ant:jar:1.8.1'

  package(:jar)
  package(:sources)
end
