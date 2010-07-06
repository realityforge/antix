gem 'buildr-iidea', :version => '=> 0.0.6'

require 'buildr_iidea'

repositories.remote << "http://www.ibiblio.org/maven2/"

desc "A set of ant tasks that are used across a range of projects"
define "antix" do
  project.version = `git describe`.strip.split('-').first
  project.group = "org.realityforge"

  project.no_ipr
  project.iml.local_repository_env_override = nil

  compile.options.source = '1.6'
  compile.options.target = '1.6'
  compile.options.lint = 'all'

  compile.with 'org.apache.ant:ant:jar:1.8.1'

  package(:jar)
end
