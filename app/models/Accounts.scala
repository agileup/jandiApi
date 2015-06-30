package models

import java.sql._
import play.api.Play.current
import play.api.db.DB
import slick.driver.PostgresDriver.simple._

case class Account (
  id: Long,
  name: String,
  status: String = "inactive",
  password: String,
  lang: String = "en",
  profileId: Option[Long] = None,
  activatedAt: Option[Timestamp] = None,
  loggedAt: Option[Timestamp] = None,
  passwordResetToken: Option[String] = None,
  passwordResetTokenAt: Option[Timestamp] = None,
  createdAt: Timestamp,
  updatedAt: Option[Timestamp] = None,
  tutoredAt: Option[Timestamp] = None
)

class Accounts(tag: Tag) extends Table[Account](tag, "accounts") {
  def id = column[Long]("id", O.AutoInc, O.PrimaryKey)
  def name = column[String]("name", O.Length(255, varying = true))
  def status = column[String]("status", O.Length(25, varying = true), O.Default("inactive"))
  def password = column[String]("password", O.Length(255, varying = true))
  def lang = column[String]("lang", O.Length(25, varying = true), O.Default("en"))
  def profileId = column[Option[Long]]("profileId", O.Default(None))
  def activatedAt = column[Option[Timestamp]]("activatedAt", O.Default(None))
  def loggedAt = column[Option[Timestamp]]("loggedAt", O.Default(None))
  def passwordResetToken = column[Option[String]]("passwordResetToken", O.Length(255, varying = true), O.Default(None))
  def passwordResetTokenAt = column[Option[Timestamp]]("passwordResetTokenAt", O.Default(None))
  def createdAt = column[Timestamp]("createdAt")
  def updatedAt = column[Option[Timestamp]]("updatedAt", O.Default(None))
  def tutoredAt = column[Option[Timestamp]]("tutoredAt", O.Default(None))

  def * = (id, name, status, password, lang, profileId, activatedAt, loggedAt, passwordResetToken, passwordResetTokenAt, createdAt, updatedAt, tutoredAt) <>(Account.tupled, Account.unapply)
}

object Accounts {

  val accounts = TableQuery[Accounts]

  def getAll(offset: Int, limit: Int): List[Account] = {
    Database.forDataSource(DB.getDataSource()) withSession { implicit session =>
      //accounts.run
      accounts.drop(offset).take(limit).list
    }
  }
  def get(id: Long): Option[Account] = {
    Database.forDataSource(DB.getDataSource()) withSession { implicit session =>
      accounts.filter(a => a.id === id).firstOption
    }
  }
  def save(account:Account): Long = {
    Database.forDataSource(DB.getDataSource()) withSession { implicit session =>
      //accounts.insert(account)
      (accounts returning accounts.map(_.id)) += account
    }
  }
}