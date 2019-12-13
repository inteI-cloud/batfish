package org.batfish.common.bdd;

import com.google.common.annotations.VisibleForTesting;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import net.sf.javabdd.BDD;
import org.batfish.datamodel.IpAccessList;
import org.batfish.datamodel.IpAccessListLine;
import org.batfish.datamodel.IpSpace;
import org.batfish.datamodel.acl.AclLineMatchExpr;

/**
 * An {@link IpAccessListToBdd} that memoizes its {@link IpAccessListToBdd#visit} method using an
 * {@link IdentityHashMap}.
 */
public final class MemoizedIpAccessListToBdd extends IpAccessListToBdd {
  private Map<IpAccessListLine, BDD> _lineCache = new IdentityHashMap<>();
  private Map<AclLineMatchExpr, BDD> _exprCache = new IdentityHashMap<>();

  public MemoizedIpAccessListToBdd(
      BDDPacket packet,
      BDDSourceManager mgr,
      Map<String, IpAccessList> aclEnv,
      Map<String, IpSpace> namedIpSpaces) {
    super(packet, mgr, new HeaderSpaceToBDD(packet, namedIpSpaces), aclEnv);
  }

  @Override
  public BDD toBdd(IpAccessListLine line) {
    return _lineCache.computeIfAbsent(line, this::visit);
  }

  @Override
  public BDD toBdd(AclLineMatchExpr expr) {
    return _exprCache.computeIfAbsent(expr, this::visit);
  }

  @VisibleForTesting
  Optional<BDD> getMemoizedBdd(AclLineMatchExpr expr) {
    return Optional.ofNullable(_exprCache.get(expr));
  }
}
