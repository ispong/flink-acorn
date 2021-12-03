mvn clean deploy -P release


<profile>
        <id>gpg</id>
        <properties>
            <gpg.executable>gpg2</gpg.executable>
            <gpg.passphrase>mypassphrase</gpg.passphrase>
        </properties>
    </profile>

<server>
        <id>sonatype-nexus-snapshots</id>
        <username>sonatype用户名</username>
        <password>sonatype密码</password>
    </server>
    <server>
        <id>sonatype-nexus-staging</id>
        <username>sonatype用户名</username>
        <password>sonatype密码</password>
    </server>