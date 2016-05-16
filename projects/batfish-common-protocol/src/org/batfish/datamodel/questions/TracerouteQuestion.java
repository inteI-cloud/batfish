package org.batfish.datamodel.questions;

import java.util.HashSet;
import java.util.Set;

import org.batfish.datamodel.FlowBuilder;

public class TracerouteQuestion extends Question {

   private Set<FlowBuilder> _flowBuilders;

   public TracerouteQuestion() {
      super(QuestionType.TRACEROUTE);
      _flowBuilders = new HashSet<FlowBuilder>();
   }

   @Override
   public boolean getDataPlane() {
      return true;
   }

   @Override
   public boolean getDifferential() {
      return false;
   }

   public Set<FlowBuilder> getFlowBuilders() {
      return _flowBuilders;
   }

   @Override
   public boolean getTraffic() {
      return true;
   }

}
