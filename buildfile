repositories.remote << 'http://repo1.maven.org/maven2'

define 'nwcfile' do
  project.version = 0.3
  package :jar

  task :run => :compile do
    system 'java -cp target/classes nwcfile.NwcFile test.nwc'
  end
end
